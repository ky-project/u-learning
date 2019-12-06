package com.ky.ulearning.system.common.constants;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @date 19/12/06 03:13
 */
public enum SystemErrorCodeEnum implements BaseEnum {

    /**
     * 后台管理系统错误状态码
     */
    PARAMETER_EMPTY(HttpStatus.BAD_REQUEST, "参数不可为空!"),
    TEACHER_NOT_EXISTS(HttpStatus.BAD_REQUEST,"教师不存在!"),
    PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "密码错误!");

    private Integer code;
    private String message;

    SystemErrorCodeEnum(HttpStatus httpStatus, String message) {
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
