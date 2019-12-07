package com.ky.ulearning.gateway.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.VerifyCodeUtil;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.redis.RedisService;
import com.ky.ulearning.gateway.remoting.TeacherRemoting;
import com.ky.ulearning.spi.common.dto.ImgResult;
import com.ky.ulearning.spi.common.dto.LoginUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.http.AuthenticationInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author luyuhao
 * @date 19/11/29 03:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private TeacherRemoting teacherRemoting;

    /**
     * 登录授权
     *
     * @param loginUser 登陆用户信息
     * @return 返回用户信息和token
     */
    @ApiOperation(value = "用户登录", notes = "将返回token和refresh_token存于cookie中，之后每次请求需带上两个token")
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginUser loginUser,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        // 查询验证码
        String code = redisService.getCodeVal(loginUser.getUuid());
        // 清除验证码
        redisService.delete(loginUser.getUuid());
        if (StringUtils.isEmpty(code)) {
            throw new BadRequestException("验证码已过期");
        }
        if (StringUtils.isEmpty(loginUser.getCode()) || !loginUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        //手动调用服务的login
        ResponseEntity responseEntity;

        teacherRemoting.login(loginUser.getUsername(), loginUser.getPassword());

//        final JwtTeacher jwtTeacher = (JwtTeacher) userDetailsService.loadUserByUsername(authorizationUser.getTeaNumber());

//        if (!jwtTeacher.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getTeaPassword()))) {
//            throw new AccountExpiredException("密码错误");
//        }
//        //账号有效判断
//        if (!jwtTeacher.isEnabled()) {
//            throw new AccountExpiredException("账号已停用，请联系管理员");
//        } else if (!jwtTeacher.isCredentialsNonExpired()) {
//            throw new AccountExpiredException("凭证已过期，请联系管理员");
//        } else if (!jwtTeacher.isAccountNonExpired()) {
//            throw new AccountExpiredException("账户已过期，请联系管理员");
//        } else if (!jwtTeacher.isAccountNonLocked()) {
//            throw new AccountExpiredException("账户已被锁定，请联系管理员");
//        }
//        // 生成令牌
//        final String token = jwtTokenUtil.generateToken(jwtTeacher);
//        final String refreshToken = jwtRefreshTokenUtil.generateRefreshToken(jwtTeacher);
//        setTokenCookie(response, token, refreshToken);
//
//        //根据userDetails构建新的Authentication
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtTeacher, null, jwtTeacher.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        //设置authentication中details
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        //更新登录时间
//        SysTeacherUpdateDTO teacherUpdateDTO = new SysTeacherUpdateDTO();
//        teacherUpdateDTO.setTeaId(jwtTeacher.getTeaId());
//        teacherUpdateDTO.setLastLoginTime(new Date());
//        teacherUpdateDTO.setUpdateTime(jwtTeacher.getUpdateTime());
//        sysTeacherService.update(teacherUpdateDTO);
//
//        // 返回 token
//        return ResponseEntity.ok(new JsonResult<>(new AuthenticationInfo(token, refreshToken), "登录成功"));
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
            log.debug("生成验证码:" + verifyCode);
            return ResponseEntity.ok(new JsonResult<>(new ImgResult("data:image/gif;base64," + Base64.encode(stream.toByteArray()), uuid), "验证码已生成"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new JsonResult<>(GatewayErrorCodeEnum.CREATE_VERIFY_CODE_FAILED));
        } finally {
            stream.close();
        }
    }

    /**
     * 根据登录类型进行选择调用哪个系统的登录接口
     *
     * @param loginUser 登录信息对象
     * @return 返回接口响应
     */
    private ResponseEntity loginSwitch(LoginUser loginUser) {
        switch (loginUser.getLoginType()) {
            case GatewayConstant.LOGIN_MANAGE_SYSTEM:
                break;
            default:
                return ResponseEntity.badRequest().body(new JsonResult<>(GatewayErrorCodeEnum.LOGIN_TYPE_MISSING));
        }
    }
}
