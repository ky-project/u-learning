package com.ky.ulearning.gateway.common.constant;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @date 19/12/07 02:11
 */
public enum  GatewayErrorCodeEnum implements BaseEnum {
    /**
     * 路由网关系统错误状态码
     */
    CREATE_VERIFY_CODE_FAILED(HttpStatus.BAD_REQUEST, "验证码生成失败!"),
    TEACHER_HAS_NO_ROLE(HttpStatus.BAD_REQUEST, "请联系管理员，您当前身份不知!"),
    USER_NOT_EXISTS(HttpStatus.BAD_REQUEST,"用户不存在!"),
    ACCOUNT_ERROR(HttpStatus.BAD_REQUEST,"账号异常，无法识别账号为教师还是学生，请联系管理员"),
    VERIFY_CODE_ERROR(HttpStatus.BAD_REQUEST,"验证码错误"),
    VERIFY_CODE_TIMEOUT(HttpStatus.BAD_REQUEST,"验证码已过期"),
    LOGIN_PASSWORD_ERROR(HttpStatus.BAD_REQUEST,"密码错误"),
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED,"请先登录"),
    INSUFFICIENT_PERMISSION(HttpStatus.FORBIDDEN,"权限不足"),
    REQUEST_PATH_NOT_REGISTER(HttpStatus.BAD_REQUEST, "访问路径非法!"),
    AUTHORIZED_FAILURE(HttpStatus.UNAUTHORIZED,"认证失败")
    ;

    private Integer code;
    private String message;

    GatewayErrorCodeEnum(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
