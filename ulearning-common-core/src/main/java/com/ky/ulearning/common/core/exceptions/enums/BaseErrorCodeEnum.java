package com.ky.ulearning.common.core.exceptions.enums;

import org.springframework.http.HttpStatus;

/**
 * 基础公共异常码
 *
 * @author luyuhao
 * @date 2019/12/5 13:07
 */
public enum BaseErrorCodeEnum {

    BUSINESS_EXCEPTION(HttpStatus.BAD_REQUEST.value(), "发生业务异常");

    private Integer code;
    private String message;

    BaseErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

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
