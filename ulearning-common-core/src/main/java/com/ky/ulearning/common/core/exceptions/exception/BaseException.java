package com.ky.ulearning.common.core.exceptions.exception;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * 基础异常类
 *
 * @author luyuhao
 * @date 20/01/07 23:50
 */
public class BaseException extends RuntimeException {
    private Integer status = HttpStatus.BAD_REQUEST.value();

    public BaseException(Integer status) {
        this.status = status;
    }

    public BaseException(BaseEnum baseEnum) {
        super(baseEnum.getMessage());
        this.status = baseEnum.getCode();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getStatus() {
        return status;
    }
}
