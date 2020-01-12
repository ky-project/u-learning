package com.ky.ulearning.gateway.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.*;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.redis.RedisService;
import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.gateway.common.security.JwtAccountDetailsService;
import com.ky.ulearning.gateway.common.utils.JwtAccountUtil;
import com.ky.ulearning.gateway.common.utils.JwtRefreshTokenUtil;
import com.ky.ulearning.gateway.common.utils.JwtTokenUtil;
import com.ky.ulearning.gateway.remoting.MonitorManageRemoting;
import com.ky.ulearning.gateway.remoting.SystemManageRemoting;
import com.ky.ulearning.spi.common.dto.ImgResult;
import com.ky.ulearning.spi.common.dto.LoginUser;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luyuhao
 * @date 19/11/29 03:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth")
@Api(tags = "系统认证接口")
public class AuthController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SystemManageRemoting systemManageRemoting;

    @Autowired
    private JwtRefreshTokenUtil jwtRefreshTokenUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private GatewayConfigParameters gatewayConfigParameters;

    @Autowired
    private JwtAccountDetailsService jwtAccountDetailsService;

    @Autowired
    private MonitorManageRemoting monitorManageRemoting;

    @Log("获取个人权限信息")
    @ApiOperation(value = "获取个人权限信息", notes = "若为学生，则无权限信息")
    @GetMapping(value = "/permissionInfo")
    public ResponseEntity<JsonResult<List<PermissionEntity>>> getPermissionInfo() {
        JwtAccount jwtAccount = JwtAccountUtil.getUserDetails();
        ValidateHandler.checkParameter(jwtAccount == null, GatewayErrorCodeEnum.NOT_LOGGED_IN);

        String sysRole = jwtAccount.getSysRole();
        //根据不同系统角色调用不同接口
        if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
            //教师身份
            return ResponseEntityUtil.ok(JsonResult.buildData(jwtAccount.getPermissions()));
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
            //TODO 学生身份
            return ResponseEntityUtil.ok(JsonResult.buildMsg("学生无权限信息"));
        } else {
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
        }
    }

    @Log("获取个人角色信息")
    @ApiOperation(value = "获取个人角色信息", notes = "若为学生，则无角色信息")
    @GetMapping(value = "/roleInfo")
    public ResponseEntity<JsonResult<List<RoleEntity>>> getRoleInfo() {
        JwtAccount jwtAccount = JwtAccountUtil.getUserDetails();
        ValidateHandler.checkParameter(jwtAccount == null, GatewayErrorCodeEnum.NOT_LOGGED_IN);

        String sysRole = jwtAccount.getSysRole();
        //根据不同系统角色调用不同接口
        if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
            //教师身份
            return ResponseEntityUtil.ok(JsonResult.buildData(jwtAccount.getRoles()));
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
            //TODO 学生身份
            return ResponseEntityUtil.ok(JsonResult.buildMsg("学生无角色信息"));
        } else {
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
        }
    }

    @Log("获取个人信息")
    @ApiOperation(value = "获取个人信息")
    @GetMapping(value = "/info")
    public ResponseEntity getUserInfo() {
        JwtAccount jwtAccount = JwtAccountUtil.getUserDetails();
        ValidateHandler.checkParameter(jwtAccount == null, GatewayErrorCodeEnum.NOT_LOGGED_IN);

        String sysRole = jwtAccount.getSysRole();

        //根据不同系统角色调用不同接口
        if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
            //教师身份
            TeacherEntity teacher = systemManageRemoting.getById(jwtAccount.getId()).getData();
            return ResponseEntityUtil.ok(JsonResult.buildData(teacher));
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
            //TODO 学生身份
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
        } else {
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum((GatewayErrorCodeEnum.ACCOUNT_ERROR)));
        }
    }

    /**
     * 登出成功
     *
     * @return 返回用户信息和token
     */
    @Log("成功退出系统")
    @ApiOperation(value = "", hidden = true)
    @GetMapping(value = "/logout/success")
    public ResponseEntity logoutSuccess() {
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(null, "安全退出"));
    }

    /**
     * 登录授权
     *
     * @param loginUser 登陆用户信息
     * @return 返回用户信息和token
     */
    @ApiOperation(value = "登录系统-单点登录", notes = "将返回token和refresh_token存于cookie中，之后每次请求需带上两个token")
    @PostMapping("/login")
    public ResponseEntity<JsonResult> login(LoginUser loginUser,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        long currentTime = System.currentTimeMillis();
        // 查询验证码
        String code = redisService.getCodeVal(loginUser.getUuid());
        loginUser.setUsername(loginUser.getUsername().trim());
        loginUser.setPassword(loginUser.getPassword().trim());
        // 清除验证码
        redisService.delete(loginUser.getUuid());
        ValidateHandler.checkParameter(StringUtils.isEmpty(code), GatewayErrorCodeEnum.VERIFY_CODE_TIMEOUT);
        ValidateHandler.checkParameter(StringUtils.isEmpty(loginUser.getCode())
                || !loginUser.getCode().equalsIgnoreCase(code), GatewayErrorCodeEnum.VERIFY_CODE_ERROR);

        //手动获取登录用户信息
        JwtAccount jwtAccount = (JwtAccount) jwtAccountDetailsService.loadUserByUsername(loginUser.getUsername());

        ValidateHandler.checkParameter(!jwtAccount.getPassword().equals(EncryptUtil.encryptPassword(loginUser.getPassword())), GatewayErrorCodeEnum.LOGIN_PASSWORD_ERROR);

        //账号有效判断
        if (!jwtAccount.isEnabled()) {
            throw new BadRequestException("账号已停用，请联系管理员");
        } else if (!jwtAccount.isCredentialsNonExpired()) {
            throw new BadRequestException("凭证已过期，请联系管理员");
        } else if (!jwtAccount.isAccountNonExpired()) {
            throw new BadRequestException("账户已过期，请联系管理员");
        } else if (!jwtAccount.isAccountNonLocked()) {
            throw new BadRequestException("账户已被锁定，请联系管理员");
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
        if (MicroConstant.SYS_ROLE_TEACHER.equals(jwtAccount.getSysRole())) {
            Map<String, Object> teacherEntity = new HashMap<>(16);
            teacherEntity.put("id", jwtAccount.getId());
            teacherEntity.put("lastLoginTime", new Date());
            teacherEntity.put("updateTime", jwtAccount.getUpdateTime());
            systemManageRemoting.update(teacherEntity);
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(jwtAccount.getSysRole())) {
            //TODO 更新学生登录时间
        }

        //登录日志
        try {
            //保存log信息
            logRecord(loginUser, currentTime);
        } catch (Exception e) {
            log.error("监控系统未启动");
        }
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(map, "登录成功"));
    }

    /**
     * 获取验证码
     */
    @Log("获取验证码")
    @ApiOperation(value = "获取验证码", notes = "每次登录前必须先调用该api获取验证码，登录时需带上uuid和用户填写的验证码<br/>返回的img属性在img标签中使用，src=返回的img串")
    @GetMapping(value = "/vCode")
    public ResponseEntity<JsonResult<ImgResult>> getCode() throws IOException {
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
            return ResponseEntityUtil.ok(JsonResult.buildDataMsg(new ImgResult("data:image/gif;base64," + Base64.encode(stream.toByteArray()), uuid), "验证码已生成"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum((GatewayErrorCodeEnum.CREATE_VERIFY_CODE_FAILED)));
        } finally {
            stream.close();
        }
    }

    private void setTokenCookie(HttpServletResponse response, String token, String refreshToken) {
        Cookie tokenCookie = new Cookie(GatewayConstant.COOKIE_TOKEN, token);
        tokenCookie.setMaxAge((int) (gatewayConfigParameters.getRefreshExpiration() / 1000));
        tokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie(GatewayConstant.COOKIE_REFRESH_TOKEN, refreshToken);
        refreshTokenCookie.setMaxAge((int) (gatewayConfigParameters.getRefreshExpiration() / 1000));
        refreshTokenCookie.setPath("/");

        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private void logRecord(LoginUser loginUser, long currentTime) {
        //设置log属性
        LogEntity logEntity = new LogEntity();
        //获取用户信息
        logEntity.setLogUsername(JwtAccountUtil.getUsername());
        logEntity.setLogDescription("登录系统");
        logEntity.setLogModule("com.ky.ulearning.gateway.controller.AuthController.login()");
        logEntity.setLogIp(IpUtil.getIP(RequestHolderUtil.getHttpServletRequest()));
        logEntity.setLogType("INFO");
        logEntity.setLogParams(loginUser.toString());
        logEntity.setLogTime(System.currentTimeMillis() - currentTime);
        logEntity.setLogAddress(IpUtil.getCityInfo(logEntity.getLogIp()));
        logEntity.setCreateBy("system");
        logEntity.setUpdateBy("system");

        Map<String, Object> logMap =
                JSONObject.parseObject(JSON.toJSONString(logEntity,
                        SerializerFeature.WriteNullStringAsEmpty,
                        SerializerFeature.WriteNullNumberAsZero,
                        SerializerFeature.WriteMapNullValue));

        monitorManageRemoting.add(logMap);
    }
}
