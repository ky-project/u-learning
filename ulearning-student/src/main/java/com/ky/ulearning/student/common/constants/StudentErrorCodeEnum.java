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
    DOCUMENTATION_PARENT_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料父节点id不能为空"),
    RESOURCE_PARENT_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学资源父节点id不能为空"),
    EXPERIMENT_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验id不能为空"),
    EXPERIMENT_RESULT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验结果和实验附件不能都为空"),
    EXAMINATION_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "测试任务id不能为空"),

    NOTICE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "通告不存在"),
    NOTICE_ATTACHMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "通告附件不存在"),
    COURSE_DOCUMENTATION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教师无分享的文件资料"),
    COURSE_FILE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "课程文件不存在"),
    COURSE_ID_NOT_EXISTS(HttpStatus.BAD_REQUEST, "课程id不存在"),
    DOCUMENTATION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "文件资料不存在"),
    COURSE_RESOURCE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教师无分享的教学资源"),
    RESOURCE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教学资源不存在"),
    EXPERIMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "实验不存在"),
    EXPERIMENT_ATTACHMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "实验附件不存在"),
    EXPERIMENT_RESULT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "实验结果不存在"),
    EXAMINATION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "测试任务不存在"),

    STUDENT_TEACHING_TASK_SELECTED_ILLEGAL(HttpStatus.BAD_REQUEST, "无法重复选课"),
    STUDENT_TEACHING_TASK_CANCEL_SELECTED_ILLEGAL(HttpStatus.BAD_REQUEST, "当前未选该课程"),
    TEACHING_TASK_NOTICE_ILLEGAL(HttpStatus.BAD_REQUEST, "当前未选该课程，无法访问该通告"),
    NOTICE_ATTACHMENT_ILLEGAL(HttpStatus.BAD_REQUEST, "通告附件已失效"),
    COURSE_FILE_ILLEGAL(HttpStatus.BAD_REQUEST, "无权限操作该课程文件，请确认是否已选课"),
    COURSE_FILE_TIME_OUT(HttpStatus.BAD_REQUEST, "该课程文件已失效"),
    ATTACHMENT_ILLEGAL(HttpStatus.BAD_REQUEST, "附件已过期"),
    EXPERIMENT_RESULT_ILLEGAL(HttpStatus.BAD_REQUEST, "实验结果已提交，无法重复提交"),

    TEACHING_TASK_ID_ERROR(HttpStatus.BAD_REQUEST, "教学任务id错误"),
    COURSE_FOLDER_CANNOT_DOWNLOAD(HttpStatus.BAD_REQUEST, "暂不支持文件夹下载"),
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
