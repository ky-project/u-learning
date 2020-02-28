package com.ky.ulearning.common.core.constant;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * 服务通用错误码
 *
 * @author luyuhao
 * @since 20/01/29 03:57
 */
public enum CommonErrorCodeEnum implements BaseEnum {
    /**
     * 服务统一错误状态码
     */
    FILE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件不能为空!"),
    FILE_TYPE_TAMPER(HttpStatus.BAD_REQUEST, "文件类型篡改!"),
    FILE_TYPE_ERROR(HttpStatus.BAD_REQUEST, "文件类型不支持!"),
    FILE_SIZE_ERROR(HttpStatus.BAD_REQUEST, "文件过大!"),
    OLD_PASSWORD_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "旧密码不能为空"),
    NEW_PASSWORD_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "新密码不能为空"),
    OLD_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "旧密码错误"),
    PASSWORD_SAME(HttpStatus.BAD_REQUEST, "旧密码与新密码相同"),
    ;

    private Integer code;
    private String message;

    CommonErrorCodeEnum(HttpStatus httpStatus, String message) {
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
