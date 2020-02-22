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

    STUDENT_TEACHING_TASK_SELECTED_ILLEGAL(HttpStatus.BAD_REQUEST, "无法重复选课"),
    STUDENT_TEACHING_TASK_CANCEL_SELECTED_ILLEGAL(HttpStatus.BAD_REQUEST, "当前未选该课程"),
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
