package com.ky.ulearning.gateway.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.EncryptUtil;
import com.ky.ulearning.common.core.utils.VerifyCodeUtil;
import com.ky.ulearning.gateway.config.GatewayConfig;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.redis.RedisService;
import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.gateway.common.security.JwtAccountDetailsService;
import com.ky.ulearning.gateway.common.util.JwtRefreshTokenUtil;
import com.ky.ulearning.gateway.common.util.JwtTokenUtil;
import com.ky.ulearning.gateway.remoting.TeacherRemoting;
import com.ky.ulearning.spi.common.dto.ImgResult;
import com.ky.ulearning.spi.common.dto.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luyuhao
 * @date 19/11/29 03:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth")
@Api(tags = "单点登录接口", description = "单点登录相关接口")
public class AuthController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private TeacherRemoting teacherRemoting;

    @Autowired
    private JwtRefreshTokenUtil jwtRefreshTokenUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private GatewayConfig gatewayConfig;

    @Autowired
    private JwtAccountDetailsService jwtAccountDetailsService;

    /**
     * 登出成功
     *
     * @return 返回用户信息和token
     */
    @ApiOperation(value = "", hidden = true)
    @GetMapping(value = "/logout/success")
    public ResponseEntity logoutSuccess() {
        return ResponseEntity.ok(new JsonResult<>(null, "安全退出"));
    }

    /**
     * 登录授权
     *
     * @param loginUser 登陆用户信息
     * @return 返回用户信息和token
     */
    @Log("登录系统-单点登录")
    @ApiOperation(value = "登录系统-单点登录", notes = "将返回token和refresh_token存于cookie中，之后每次请求需带上两个token")
    @PostMapping("/login")
    public ResponseEntity login(@Validated LoginUser loginUser,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        // 查询验证码
        String code = redisService.getCodeVal(loginUser.getUuid());
        loginUser.setUsername(loginUser.getUsername().trim());
        loginUser.setPassword(loginUser.getPassword().trim());
        // 清除验证码
        redisService.delete(loginUser.getUuid());
        if (StringUtils.isEmpty(code)) {
            throw new BadRequestException(GatewayErrorCodeEnum.VERIFY_CODE_TIMEOUT);
        }
        if (StringUtils.isEmpty(loginUser.getCode()) || !loginUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException(GatewayErrorCodeEnum.VERIFY_CODE_ERROR);
        }

        //手动获取登录用户信息
        JwtAccount jwtAccount = (JwtAccount) jwtAccountDetailsService.loadUserByUsername(loginUser.getUsername());

        if (!jwtAccount.getPassword().equals(EncryptUtil.encryptPassword(loginUser.getPassword()))) {
            throw new AccountExpiredException(GatewayErrorCodeEnum.LOGIN_PASSWORD_ERROR.getMessage());
        }

        //账号有效判断
        if (!jwtAccount.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        } else if (!jwtAccount.isCredentialsNonExpired()) {
            throw new AccountExpiredException("凭证已过期，请联系管理员");
        } else if (!jwtAccount.isAccountNonExpired()) {
            throw new AccountExpiredException("账户已过期，请联系管理员");
        } else if (!jwtAccount.isAccountNonLocked()) {
            throw new AccountExpiredException("账户已被锁定，请联系管理员");
        }
        // 生成令牌
        final String token = jwtTokenUtil.generateToken(jwtAccount);
        final String refreshToken = jwtRefreshTokenUtil.generateRefreshToken(jwtAccount);
        setTokenCookie(response, token, refreshToken);

        //根据userDetails构建新的Authentication
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAccount, null, jwtAccount.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置authentication中details
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 返回 token
        Map<String, Object> map = new HashMap<>(16);
        map.put("token", token);
        map.put("refreshToken", refreshToken);

        //根据角色登录日期更新
        if (GatewayConstant.SYS_ROLE_TEACHER.equals(jwtAccount.getSysRole())) {
            Map<String, Object> teacherEntity = new HashMap<>(16);
            teacherEntity.put("id", jwtAccount.getId());
            teacherEntity.put("lastLoginTime", new Date());
            teacherEntity.put("updateTime", jwtAccount.getUpdateTime());
            teacherRemoting.update(teacherEntity);
        } else if (GatewayConstant.SYS_ROLE_STUDENT.equals(jwtAccount.getSysRole())) {
            //TODO 更新学生登录时间
        }
        return ResponseEntity.ok(new JsonResult<>(map, "登录成功"));
    }

    /**
     * 获取验证码
     */
    @ApiOperation(value = "获取验证码", notes = "每次登录前必须先调用该api获取验证码，登录时需带上uuid和用户填写的验证码<br/>返回的img属性在img标签中使用，src=返回的img串")
    @GetMapping(value = "/vCode")
    public ResponseEntity getCode() throws IOException {
        //生成随机字串
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4);
        String uuid = IdUtil.simpleUUID();
        redisService.saveCode(uuid, verifyCode);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtil.outputImage(w, h, stream, verifyCode);
        try {
            log.info("生成验证码:" + verifyCode);
            return ResponseEntity.ok(new JsonResult<>(new ImgResult("data:image/gif;base64," + Base64.encode(stream.toByteArray()), uuid), "验证码已生成"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new JsonResult<>(GatewayErrorCodeEnum.CREATE_VERIFY_CODE_FAILED));
        } finally {
            stream.close();
        }
    }

    private void setTokenCookie(HttpServletResponse response, String token, String refreshToken) {
        Cookie tokenCookie = new Cookie(GatewayConstant.COOKIE_TOKEN, token);
        tokenCookie.setMaxAge((int) (gatewayConfig.getRefreshExpiration() / 1000));
        tokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie(GatewayConstant.COOKIE_REFRESH_TOKEN, refreshToken);
        refreshTokenCookie.setMaxAge((int) (gatewayConfig.getRefreshExpiration() / 1000));
        refreshTokenCookie.setPath("/");

        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);
    }
}
