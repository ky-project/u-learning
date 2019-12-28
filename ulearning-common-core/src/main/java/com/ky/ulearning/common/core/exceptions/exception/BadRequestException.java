package com.ky.ulearning.common.core.exceptions.exception;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 客户端请求错误异常
 *
 * @author luyuhao
 * @date 2019/12/7 10:52
 */
@Getter
public class BadRequestException extends RuntimeException {
    private Integer status = HttpStatus.BAD_REQUEST.value();

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }

    public BadRequestException(BaseEnum baseEnum) {
        super(baseEnum.getMessage());
        this.status = baseEnum.getCode();
    }
}
