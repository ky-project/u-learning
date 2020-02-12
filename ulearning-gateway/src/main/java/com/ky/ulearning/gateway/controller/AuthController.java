package com.ky.ulearning.gateway.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.*;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
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
import com.ky.ulearning.gateway.common.utils.SendMailUtil;
import com.ky.ulearning.gateway.remoting.MonitorManageRemoting;
import com.ky.ulearning.gateway.remoting.SystemManageRemoting;
import com.ky.ulearning.spi.common.dto.ForgetPasswordDto;
import com.ky.ulearning.spi.common.dto.ImgResult;
import com.ky.ulearning.spi.common.dto.LoginUser;
import com.ky.ulearning.spi.common.dto.PasswordUpdateDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author luyuhao
 * @date 19/11/29 03:10
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth")
@Api(tags = "系统认证接口")
public class AuthController extends BaseController {

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

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private SendMailUtil sendMailUtil;

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
            //学生身份
            return ResponseEntityUtil.ok(JsonResult.buildMsg("学生无权限信息"));
        } else {
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
        }
    }

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
            //学生身份
            return ResponseEntityUtil.ok(JsonResult.buildMsg("学生无角色信息"));
        } else {
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
        }
    }

    @ApiOperation(value = "获取个人信息")
    @GetMapping(value = "/info")
    public ResponseEntity<JsonResult> getUserInfo() {
        JwtAccount jwtAccount = JwtAccountUtil.getUserDetails();
        ValidateHandler.checkParameter(jwtAccount == null, GatewayErrorCodeEnum.NOT_LOGGED_IN);

        String sysRole = jwtAccount.getSysRole();

        //根据不同系统角色调用不同接口
        if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
            //教师身份
            TeacherEntity teacher = systemManageRemoting.teacherGetById(jwtAccount.getId()).getData();
            return ResponseEntityUtil.ok(JsonResult.buildData(teacher));
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
            //学生身份
            StudentEntity student = systemManageRemoting.studentGetById(jwtAccount.getId()).getData();
            return ResponseEntityUtil.ok(JsonResult.buildData(student));
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
        return ResponseEntityUtil.ok(JsonResult.buildMsg("安全退出"));
    }

    /**
     * 登录授权
     *
     * @param loginUser 登陆用户信息
     * @return 返回用户信息和token
     */
    @ApiOperation(value = "登录系统", notes = "将返回token和refresh_token存于cookie中，之后每次请求需带上两个token")
    @PostMapping("/login")
    public ResponseEntity<JsonResult> login(LoginUser loginUser,
                                            HttpServletRequest request,
                                            HttpServletResponse response) {
        long currentTime = System.currentTimeMillis();
        // 查询验证码
        String code = redisService.getCodeVal(loginUser.getUuid());
//        loginUser.setUsername(loginUser.getUsername().trim());
//        loginUser.setPassword(loginUser.getPassword().trim());
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
        Map<String, Object> updateMap = new HashMap<>(16);
        updateMap.put("id", jwtAccount.getId());
        updateMap.put("lastLoginTime", new Date());
        if (MicroConstant.SYS_ROLE_TEACHER.equals(jwtAccount.getSysRole())) {
            //更新教师登录时间
            systemManageRemoting.teacherUpdate(updateMap);
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(jwtAccount.getSysRole())) {
            //更新学生登录时间
            systemManageRemoting.studentUpdate(updateMap);
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
                JSONObject.parseObject(JsonUtil.toJsonString(logEntity));

        monitorManageRemoting.add(logMap);
    }

    @Log("修改个人信息")
    @ApiOperation(value = "修改个人信息", notes = "根据登录端不同，只需填写对应信息即可")
    @ApiOperationSupport(ignoreParameters = {"id", "teaNumber", "teaPassword", "stuNumber", "stuPassword"})
    @PostMapping(value = "/update/info")
    public ResponseEntity<JsonResult> updateInfo(TeacherDto teacherDto, StudentDto studentDto) {
        JwtAccount jwtAccount = JwtAccountUtil.getUserDetails();
        ValidateHandler.checkParameter(jwtAccount == null, GatewayErrorCodeEnum.NOT_LOGGED_IN);

        String sysRole = jwtAccount.getSysRole();
        Map<String, Object> param = new HashMap<>(16);
        param.put("id", jwtAccount.getId());
        param.put("updateBy", jwtAccount.getUsername());
        try {
            //根据不同系统角色调用不同接口
            if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
                //教师身份
                param.put("teaDept", teacherDto.getTeaDept());
                param.put("teaEmail", teacherDto.getTeaEmail());
                param.put("teaGender", teacherDto.getTeaGender());
                param.put("teaName", teacherDto.getTeaName());
                param.put("teaPhone", teacherDto.getTeaPhone());
                param.put("teaTitle", teacherDto.getTeaTitle());
                systemManageRemoting.teacherUpdate(param);
            } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
                //学生身份
                param.put("stuClass", studentDto.getStuClass());
                param.put("stuDept", studentDto.getStuDept());
                param.put("stuEmail", studentDto.getStuEmail());
                param.put("stuGender", studentDto.getStuGender());
                param.put("stuName", studentDto.getStuName());
                param.put("stuPhone", studentDto.getStuPhone());
                systemManageRemoting.studentUpdate(param);
            } else {
                return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum((GatewayErrorCodeEnum.ACCOUNT_ERROR)));
            }
            return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
        } catch (Exception e) {
            return ResponseEntityUtil.ok(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.UPDATE_FAILED));
        }
    }

    @Log("上传头像")
    @ApiOperation("上传头像")
    @PostMapping("/uploadPhoto")
    public ResponseEntity<JsonResult> uploadPhoto(MultipartFile photo) {
        JwtAccount jwtAccount = JwtAccountUtil.getUserDetails();
        ValidatorBuilder.build()
                .on(jwtAccount == null, GatewayErrorCodeEnum.NOT_LOGGED_IN)
                //参数非空校验
                .on(photo == null || photo.isEmpty(), CommonErrorCodeEnum.FILE_CANNOT_BE_NULL)
                //文件类型篡改校验
                .on(!FileUtil.fileTypeCheck(photo), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                //文件类型校验
                .on(!FileUtil.fileTypeRuleCheck(photo, FileUtil.IMAGE_TYPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                //文件大小校验
                .on(photo.getSize() > defaultConfigParameters.getPhotoMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                .doValidate().checkResult();
        try {
            String sysRole = jwtAccount.getSysRole();
            Map<String, Object> param = new HashMap<>(16);
            param.put("id", jwtAccount.getId());
            param.put("updateBy", jwtAccount.getUsername());
            //根据不同系统角色调用不同接口
            if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
                //教师身份
                //获取当前用户信息
                TeacherEntity teacherEntity = systemManageRemoting.teacherGetById(jwtAccount.getId()).getData();
                //判断是否已有头像，有则先删除
                if (StringUtil.isNotEmpty(teacherEntity.getTeaPhoto())) {
                    fastDfsClientWrapper.deleteFile(teacherEntity.getTeaPhoto());
                }
                //保存文件
                String url = fastDfsClientWrapper.uploadFile(photo);
                //更新url
                param.put("teaPhoto", url);
                systemManageRemoting.teacherUpdate(param);
                //记录文件
                monitorManageRemoting.addFileRecord(getFileRecordDto(url, photo,
                        MicroConstant.TEACHER_TABLE_NAME, jwtAccount.getId(), jwtAccount.getUsername()));
            } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
                //学生身份
                //获取当前用户信息
                StudentEntity studentEntity = systemManageRemoting.studentGetById(jwtAccount.getId()).getData();
                //判断是否已有头像，有则先删除
                if (StringUtil.isNotEmpty(studentEntity.getStuPhoto())) {
                    fastDfsClientWrapper.deleteFile(studentEntity.getStuPhoto());
                }
                //保存文件
                String url = fastDfsClientWrapper.uploadFile(photo);
                //更新url
                param.put("stuPhoto", url);
                systemManageRemoting.studentUpdate(param);
                //记录文件
                monitorManageRemoting.addFileRecord(getFileRecordDto(url, photo,
                        MicroConstant.STUDENT_TABLE_NAME, jwtAccount.getId(), jwtAccount.getUsername()));
            } else {
                return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
            }
            //返回信息
            return ResponseEntityUtil.ok(JsonResult.buildMsg("上传成功"));
        } catch (Exception e) {
            return ResponseEntityUtil.ok(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.UPLOAD_FAILED));
        }
    }

    @Log("修改密码")
    @ApiOperation("修改密码")
    @ApiOperationSupport(ignoreParameters = "id")
    @PostMapping("/updatePassword")
    public ResponseEntity<JsonResult> updatePassword(PasswordUpdateDto passwordUpdateDto) {
        JwtAccount jwtAccount = JwtAccountUtil.getUserDetails();
        ValidatorBuilder.build()
                .on(jwtAccount == null, GatewayErrorCodeEnum.NOT_LOGGED_IN)
                .on(StringUtil.isEmpty(passwordUpdateDto.getOldPassword()), CommonErrorCodeEnum.OLD_PASSWORD_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(passwordUpdateDto.getNewPassword()), CommonErrorCodeEnum.NEW_PASSWORD_CANNOT_BE_NULL)
                .on(passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getOldPassword()), CommonErrorCodeEnum.PASSWORD_SAME)
                .doValidate().checkResult();

        String oldPassword = EncryptUtil.encryptPassword(passwordUpdateDto.getOldPassword());
        //旧密码错误
        ValidateHandler.checkParameter(!oldPassword.equals(jwtAccount.getPassword()), CommonErrorCodeEnum.OLD_PASSWORD_ERROR);
        //参数初始化
        Map<String, Object> param = new HashMap<>(16);
        param.put("id", jwtAccount.getId());
        param.put("updateBy", jwtAccount.getUsername());
        String sysRole = jwtAccount.getSysRole();

        //根据不同系统角色调用不同接口
        if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
            //教师身份
            param.put("teaPassword", passwordUpdateDto.getNewPassword());
            systemManageRemoting.teacherUpdate(param);
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
            //学生身份
            param.put("stuPassword", passwordUpdateDto.getNewPassword());
            systemManageRemoting.studentUpdate(param);
        } else {
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
        }
        //返回信息
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("发送修改密码邮件")
    @ApiOperation(value = "发送修改密码邮件", notes = "忘记密码时使用")
    @ApiOperationSupport(ignoreParameters = {"password", "code", "uuid"})
    @PostMapping("/sendUpdatePwdEmail")
    public ResponseEntity<JsonResult<String>> sendUpdatePwdEmail(ForgetPasswordDto forgetPasswordDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(forgetPasswordDto.getEmail()), GatewayErrorCodeEnum.EMAIL_CANNOT_BE_NULL);
        //获取学生教师信息
        TeacherEntity teacherEntity = getByTeaEmail(forgetPasswordDto.getEmail());
        StudentEntity studentEntity = getByStuEmail(forgetPasswordDto.getEmail());
        //都不存在时
        if (teacherEntity == null && studentEntity == null) {
            throw new BadRequestException(GatewayErrorCodeEnum.EMAIL_NOT_EXISTS);
        }
        //账号都存在时
        if (teacherEntity != null && studentEntity != null) {
            throw new BadRequestException(GatewayErrorCodeEnum.EMAIL_ERROR);
        }
        String username;
        if (teacherEntity != null) {
            forgetPasswordDto.setId(teacherEntity.getId())
                    .setSysRole(MicroConstant.SYS_ROLE_TEACHER)
                    .setUsername(teacherEntity.getTeaNumber());
            username = teacherEntity.getTeaName();
        } else {
            forgetPasswordDto.setId(studentEntity.getId())
                    .setSysRole(MicroConstant.SYS_ROLE_STUDENT)
                    .setUsername(studentEntity.getStuNumber());
            username = studentEntity.getStuName();
        }
        //生成随机字串
        String code = VerifyCodeUtil.generateVerifyCode(6, VerifyCodeUtil.NUMBER_VERIFY_CODES);
        String uuid = IdUtil.simpleUUID();
        forgetPasswordDto.setCode(code);
        redisService.saveCode(uuid, JsonUtil.toJsonString(forgetPasswordDto));
        //发送邮件
        sendMailUtil.sendVerifyCodeMail(username, code, forgetPasswordDto.getEmail());
        return ResponseEntityUtil.ok(JsonResult.buildData(uuid));
    }

    /**
     * 根据邮箱查询教师信息
     *
     * @param teaEmail 教师邮箱
     * @return 教师对象
     */
    private TeacherEntity getByTeaEmail(String teaEmail) {
        try {
            JsonResult<TeacherEntity> jsonResult = systemManageRemoting.getByTeaEmail(teaEmail);
            return Optional.ofNullable(jsonResult)
                    .map(JsonResult::getData)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据邮箱查询学生信息
     *
     * @param stuEmail 学生邮箱
     * @return 学生对象
     */
    private StudentEntity getByStuEmail(String stuEmail) {
        try {
            JsonResult<StudentEntity> jsonResult = systemManageRemoting.getByStuEmail(stuEmail);
            return Optional.ofNullable(jsonResult)
                    .map(JsonResult::getData)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Log("通过邮箱修改密码")
    @ApiOperation(value = "通过邮箱修改密码", notes = "忘记密码时使用")
    @ApiOperationSupport(ignoreParameters = {"email"})
    @PostMapping("/updatePwdByEmail")
    public ResponseEntity<JsonResult> updatePwdByEmail(ForgetPasswordDto forgetPasswordDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(forgetPasswordDto.getPassword()), CommonErrorCodeEnum.NEW_PASSWORD_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(forgetPasswordDto.getCode()), GatewayErrorCodeEnum.VERIFY_CODE_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(forgetPasswordDto.getUuid()), GatewayErrorCodeEnum.LOST_PARAMETERS)
                .doValidate().checkResult();
        // 查询验证码
        String jsonStr = redisService.getCodeVal(forgetPasswordDto.getUuid());
        // 清除验证码
        redisService.delete(forgetPasswordDto.getUuid());
        ValidateHandler.checkParameter(StringUtils.isEmpty(jsonStr), GatewayErrorCodeEnum.VERIFY_CODE_TIMEOUT);
        //获取uuid对应的dto
        ForgetPasswordDto checkDto = JsonUtil.parseObject(jsonStr, ForgetPasswordDto.class);
        //判断验证码是否正确
        ValidateHandler.checkParameter(! forgetPasswordDto.getCode().trim().equals(checkDto.getCode()), GatewayErrorCodeEnum.VERIFY_CODE_ERROR);

        //参数初始化
        Map<String, Object> param = new HashMap<>(4);
        param.put("id", checkDto.getId());
        param.put("updateBy", checkDto.getUsername());
        String sysRole = checkDto.getSysRole();

        //根据不同系统角色调用不同接口
        if (MicroConstant.SYS_ROLE_TEACHER.equals(sysRole)) {
            //教师身份
            param.put("teaPassword", forgetPasswordDto.getPassword());
            systemManageRemoting.teacherUpdate(param);
        } else if (MicroConstant.SYS_ROLE_STUDENT.equals(sysRole)) {
            //学生身份
            param.put("stuPassword", forgetPasswordDto.getPassword());
            systemManageRemoting.studentUpdate(param);
        } else {
            return ResponseEntityUtil.badRequest(JsonResult.buildErrorEnum(GatewayErrorCodeEnum.ACCOUNT_ERROR));
        }
        //返回信息
        return ResponseEntityUtil.ok(JsonResult.buildMsg("修改成功"));
    }
}
