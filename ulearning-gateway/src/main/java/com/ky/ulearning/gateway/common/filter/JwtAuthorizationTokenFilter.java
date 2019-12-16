package com.ky.ulearning.gateway.common.filter;

import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.ky.ulearning.gateway.common.exception.JwtTokenException;
import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.gateway.common.security.JwtAuthenticationFailureHandler;
import com.ky.ulearning.gateway.common.utils.JwtRefreshTokenUtil;
import com.ky.ulearning.gateway.common.utils.JwtTokenUtil;
import com.sun.xml.fastinfoset.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * token校验过滤器
 *
 * @author luyuhao
 * @date 2019/12/10 9:23
 */
@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    /**
     * 不需要过滤的url
     */
    private final static String[] NOT_FILTER_URL = {"/auth/login", "/auth/logout", "/auth/vCode"};

    private final static String PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtRefreshTokenUtil jwtRefreshTokenUtil;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final GatewayConfigParameters gatewayConfigParameters;

    public JwtAuthorizationTokenFilter(@Qualifier("jwtAccountDetailsService") UserDetailsService userDetailsService,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtRefreshTokenUtil jwtRefreshTokenUtil,
                                       JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler,
                                       GatewayConfigParameters gatewayConfigParameters) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtRefreshTokenUtil = jwtRefreshTokenUtil;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.gatewayConfigParameters = gatewayConfigParameters;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        for (String chainUri : NOT_FILTER_URL) {
            if (uri.contains(chainUri)) {
                chain.doFilter(request, response);
                return;
            }
        }

        //获取请求头
        String tokenHeader = request.getHeader(gatewayConfigParameters.getTokenHeader());
        String refreshTokenHeader = request.getHeader(gatewayConfigParameters.getRefreshTokenHeader());

        String token;
        String refreshToken;
        String username;
        try {
            //token空值校验
            if (tokenHeader == null || refreshTokenHeader == null) {
                chain.doFilter(request, response);
                return;
            }
            //字符转义
            tokenHeader = URLDecoder.decode(tokenHeader, Encoder.UTF_8).trim();
            refreshTokenHeader = URLDecoder.decode(refreshTokenHeader, Encoder.UTF_8).trim();
            if (!tokenHeader.startsWith(PREFIX)
                    || !refreshTokenHeader.startsWith(PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            //提取token
            token = tokenHeader.substring(PREFIX.length());
            refreshToken = refreshTokenHeader.substring(PREFIX.length());

            //防止token被篡改
            if (!jwtTokenUtil.tamperProof(token) || !jwtRefreshTokenUtil.tamperProof(refreshToken)) {
                throw new JwtTokenException("token被篡改，请重新登录");
            }
            //获取用户账号
            username = jwtTokenUtil.getUsernameFromToken(token);
            //通过用户编号获取用户
            JwtAccount account = (JwtAccount) userDetailsService.loadUserByUsername(username);
            //双token刷新
            if (!jwtTokenUtil.validateToken(token, account) && jwtRefreshTokenUtil.validateRefreshToken(refreshToken, account)) {
                //token过期,refresh_token未过期 -> 刷新token
                String newToken = jwtTokenUtil.refreshToken(token);
                setTokenCookie(response, GatewayConstant.COOKIE_TOKEN, newToken);
            } else if (jwtTokenUtil.validateToken(token, account) && !jwtRefreshTokenUtil.validateRefreshToken(refreshToken, account)) {
                //token未过期,refresh_token过期 -> 刷新refresh_token
                String newRefreshToken = jwtRefreshTokenUtil.refreshRefreshToken(refreshToken);
                setTokenCookie(response, GatewayConstant.COOKIE_REFRESH_TOKEN, newRefreshToken);
            } else if (!jwtTokenUtil.validateToken(token, account) && !jwtRefreshTokenUtil.validateRefreshToken(refreshToken, account)) {
                //token过期,refresh_token过期 -> 登录失效
                deleteTokenCookie(request, GatewayConstant.COOKIE_TOKEN);
                deleteTokenCookie(request, GatewayConstant.COOKIE_REFRESH_TOKEN);
                throw new JwtTokenException("登录已失效，请重新登录");
            }

            //每次请求重设用户信息
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException ae) {
            ae.printStackTrace();
            //交给自定义的AuthenticationFailureHandler
            jwtAuthenticationFailureHandler.onAuthenticationFailure(request, response, ae);
            return;
        }
        chain.doFilter(request, response);
    }

    private void deleteTokenCookie(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    cookie.setMaxAge(0);
                    break;
                }
            }
        }
    }

    private void setTokenCookie(HttpServletResponse response, String tokenName, String tokenValue) {
        Cookie tokenCookie = new Cookie(tokenName, tokenValue);
        tokenCookie.setMaxAge((int) (gatewayConfigParameters.getRefreshExpiration() / 1000));
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
    }
}
