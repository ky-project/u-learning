package com.ky.ulearning.monitor.common.constants;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @since 20/02/06 20:59
 */
public enum MonitorManageErrorCodeEnum implements BaseEnum {

    /**
     * 监控系统错误状态码
     */
    ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "id不能为空"),

    FILE_RECORD_NOT_EXISTS(HttpStatus.BAD_REQUEST, "文件记录不存在"),
    FILE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "文件不存在"),
    FILE_ILLEGAL(HttpStatus.BAD_REQUEST, "文件已失效"),
    TRAFFIC_DAYS_ERROR(HttpStatus.BAD_REQUEST, "查询天数必须>0")
    ;

    private Integer code;
    private String message;

    MonitorManageErrorCodeEnum(HttpStatus httpStatus, String message) {
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
