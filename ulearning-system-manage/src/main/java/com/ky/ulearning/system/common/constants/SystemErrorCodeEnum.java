package com.ky.ulearning.system.common.constants;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @date 19/12/06 03:13
 */
public enum SystemErrorCodeEnum implements BaseEnum {

    /**
     * 后台管理系统错误状态码
     */
    PARAMETER_EMPTY(HttpStatus.BAD_REQUEST, "参数不可为空!"),
    EMAIL_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "邮箱不可为空!"),
    TEA_NUMBER_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "工号不可为空!"),
    NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "姓名不可为空!"),
    PERMISSION_URL_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限url不可为空!"),
    PERMISSION_GROUP_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限组不可为空!"),
    PERMISSION_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限名不可为空!"),
    PERMISSION_SOURCE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限码不可为空!"),
    ROLE_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "角色名不可为空!"),
    ROLE_SOURCE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "角色资源名不可为空!"),
    IS_ADMIN_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "未指定是否是管理员角色"),
    COURSE_NUMBER_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程号不能为空"),
    COURSE_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程名不能为空"),
    COURSE_CREDIT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程学分不能为空"),
    TEA_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教师id不能为空"),
    COURSE_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程id不能为空"),
    TEACHING_TASK_ALIAS_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学任务别称不能为空"),
    TERM_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "学期不能为空"),
    COURSE_ID_NOT_EXISTS(HttpStatus.BAD_REQUEST, "课程id不存在"),
    STU_NUMBER_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "学号不能为空"),
    STU_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "姓名不能为空!"),
    ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "ID不能为空!"),
    LAST_LOGIN_TIME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "上次登录时间不能为空"),
    UPATE_TIME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "更新时间不能为空"),


    TEACHER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教师不存在!"),
    TEA_ID_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教师id不存在"),
    STUDENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "学生不存在!"),

    ;

    private Integer code;
    private String message;

    SystemErrorCodeEnum(HttpStatus httpStatus, String message) {
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
