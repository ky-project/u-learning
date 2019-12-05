package com.ky.ulearning.common.core.exceptions.enums;

import org.springframework.http.HttpStatus;

/**
 * 基础公共异常码
 *
 * @author luyuhao
 * @date 2019/12/5 13:07
 */
public enum BaseErrorCodeEnum implements BaseEnum {

    /**
     * 发生业务异常
     */
    BUSINESS_EXCEPTION(HttpStatus.BAD_REQUEST, "发生业务异常");

    private Integer code;
    private String message;

    BaseErrorCodeEnum(HttpStatus httpStatus, String message) {
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

    @Override
    public String toString() {
        return "BaseErrorCodeEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
