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
    CREATE_VERIFY_CODE_FAILED(HttpStatus.BAD_REQUEST, "验证码生成失败!")
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
