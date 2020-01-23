package com.ky.ulearning.gateway.common.filter;

import com.ky.ulearning.common.core.utils.MutableHttpServletRequest;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.sun.xml.fastinfoset.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 自动获取cookie存入header中
 *
 * @author luyuhao
 * @since 20/01/21 23:46
 */
@Slf4j
@Component
public class AccessFilter extends OncePerRequestFilter {

    private final GatewayConfigParameters gatewayConfigParameters;

    public AccessFilter(GatewayConfigParameters gatewayConfigParameters) {
        this.gatewayConfigParameters = gatewayConfigParameters;
    }

    /**
     * 默认对非静态资源的获取进行自动获取cookie操作
     */
    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        //判断是否已有请求头
        String tokenHeader = request.getHeader(gatewayConfigParameters.getTokenHeader());
        String refreshTokenHeader = request.getHeader(gatewayConfigParameters.getRefreshTokenHeader());
        if (StringUtil.isNotEmpty(tokenHeader) && StringUtil.isNotEmpty(refreshTokenHeader)) {
            chain.doFilter(request, response);
            return;
        }
        request = addHeader(request);
        chain.doFilter(request, response);
    }

    private MutableHttpServletRequest addHeader(HttpServletRequest request) throws UnsupportedEncodingException {
        MutableHttpServletRequest mutRequest = new MutableHttpServletRequest(request);
        //从cookie中获取token
        String refreshToken = getTokenCookie(request, GatewayConstant.COOKIE_REFRESH_TOKEN);
        String token = getTokenCookie(request, GatewayConstant.COOKIE_TOKEN);
        //若不为空，放入header中
        if (StringUtil.isNotEmpty(refreshToken) && StringUtil.isNotEmpty(token)) {
            mutRequest.addHeader(gatewayConfigParameters.getRefreshTokenHeader(), URLEncoder.encode(GatewayConstant.TOKEN_PREFIX + refreshToken, Encoder.UTF_8));
            mutRequest.addHeader(gatewayConfigParameters.getTokenHeader(), URLEncoder.encode(GatewayConstant.TOKEN_PREFIX + token, Encoder.UTF_8));
        }
        return mutRequest;
    }

    /**
     * 获取cookie中的token
     */
    private String getTokenCookie(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(tokenName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
