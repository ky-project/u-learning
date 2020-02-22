package com.ky.ulearning.student.common.constants;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @since 20/02/21 20:40
 */
public enum StudentErrorCodeEnum implements BaseEnum {

    /**
     * 学生端错误状态码
     */
    TEACHING_TASK_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学任务id不能为空"),
    ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "id不能为空"),
    NOTICE_ATTACHMENT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "通告附件不能为空"),

    NOTICE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "通告不存在"),
    NOTICE_ATTACHMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "通告附件不存在"),

    STUDENT_TEACHING_TASK_SELECTED_ILLEGAL(HttpStatus.BAD_REQUEST, "无法重复选课"),
    STUDENT_TEACHING_TASK_CANCEL_SELECTED_ILLEGAL(HttpStatus.BAD_REQUEST, "当前未选该课程"),
    TEACHING_TASK_NOTICE_ILLEGAL(HttpStatus.BAD_REQUEST, "当前未选该课程，无法访问该通告"),
    NOTICE_ATTACHMENT_ILLEGAL(HttpStatus.BAD_REQUEST, "通告附件已失效")
    ;

    private Integer code;
    private String message;

    StudentErrorCodeEnum(HttpStatus httpStatus, String message) {
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
