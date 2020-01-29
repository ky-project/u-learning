package com.ky.ulearning.common.core.constant;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * 服务整体错误码
 *
 * @author luyuhao
 * @date 19/12/15 14:54
 */
public enum MicroErrorCodeEnum implements BaseEnum {
    /**
     * 服务统一错误状态码
     */
    OPERATE_ERROR(HttpStatus.BAD_REQUEST, "非法操作!"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "业务处理异常!"),
    HAS_NO_PERMISSION(HttpStatus.FORBIDDEN, "权限不足!"),
    SERVER_DOWN(HttpStatus.INTERNAL_SERVER_ERROR, "服务正在重启或正在维护，请稍后再试!"),
    FILE_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "文件上传失败!")
    ;

    private Integer code;
    private String message;

    MicroErrorCodeEnum(HttpStatus httpStatus, String message) {
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
