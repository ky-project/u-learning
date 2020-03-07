package com.ky.ulearning.teacher.common.constants;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.bouncycastle.jcajce.provider.symmetric.TEA;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @since 20/01/26 21:37
 */
public enum TeacherErrorCodeEnum implements BaseEnum {

    /**
     * 教师端错误状态码
     */
    COURSE_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程id不能为空"),
    TEACHING_TASK_ALIAS_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学任务别称不能为空"),
    TERM_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "学期不能为空"),
    ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "id不能为空"),
    TEACHING_TASK_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学任务id不能为空"),
    STUDENT_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "学生id不能为空"),
    NOTICE_TITLE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "标题不能为空"),
    NOTICE_ATTACHMENT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "附件不能为空"),
    QUESTION_KNOWLEDGE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "知识模块不能为空"),
    QUESTION_TYPE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "试题类型不能为空"),
    QUESTION_TEXT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "试题内容不能为空"),
    EXPERIMENT_ATTACHMENT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "附件不能为空"),
    EXPERIMENT_ORDER_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验序号不能为空"),
    EXPERIMENT_TITLE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验标题不能为空"),
    EXPERIMENT_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "实验id不能为空"),
    EXAMINATION_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "测试任务名称不能为空"),
    EXAMINATION_DURATION_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "测试时长不能为空"),
    EXAMINATION_STATE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "测试任务状态不能为空"),
    EXAMINATION_PARAMETERS_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "试题参数不能为空"),
    EXAMINATION_SHOW_RESULT_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "是否反馈测试结果不能为空"),
    DOCUMENTATION_FILE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料不能为空"),
    DOCUMENTATION_TITLE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料标题不能为空"),
    DOCUMENTATION_CATEGORY_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料分类不能为空"),
    DOCUMENTATION_SHARED_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料是否共享不能为空"),
    DOCUMENTATION_PATH_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料所属文件夹id不能为空"),
    DOCUMENTATION_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料id不能为空"),
    COURSE_FILE_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程文件名不能为空"),
    COURSE_FILE_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "课程文件id不能为空"),
    RESOURCE_FILE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学资料不能为空"),
    RESOURCE_TITLE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学资料标题不能为空"),
    RESOURCE_TYPE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学资料类型不能为空"),
    RESOURCE_SHARED_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学资料是否共享不能为空"),
    RESOURCE_PATH_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "教学资源所属文件夹id不能为空"),
    RESOURCE_ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "文件资料id不能为空"),
    COURSE_FOLDER_CANNOT_DOWNLOAD(HttpStatus.BAD_REQUEST, "文件夹下载暂时不支持"),

    TEA_NUMBER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教师工号不存在"),
    COURSE_ID_NOT_EXISTS(HttpStatus.BAD_REQUEST, "课程id不存在"),
    NOTICE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "通告不存在"),
    NOTICE_ATTACHMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "附件不存在"),
    TEACHING_TASK_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教学任务不存在"),
    COURSE_QUESTION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "试题不存在"),
    EXPERIMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "实验不存在"),
    EXPERIMENT_ATTACHMENT_NOT_EXISTS(HttpStatus.BAD_REQUEST, "附件不存在"),
    EXAMINATION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "测试任务不存在"),
    DOCUMENTATION_NOT_EXISTS(HttpStatus.BAD_REQUEST, "文件资料不存在"),
    COURSE_FILE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "课程文件不存在"),
    RESOURCE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教学资源不存在"),

    NOTICE_ATTACHMENT_ILLEGAL(HttpStatus.BAD_REQUEST, "附件已过期"),
    TEACHING_TASK_ID_ILLEGAL(HttpStatus.BAD_REQUEST, "该教学任务不可操作"),
    STUDENT_ILLEGAL(HttpStatus.BAD_REQUEST, "该学生不可操作"),
    COURSE_ILLEGAL(HttpStatus.BAD_REQUEST, "该课程不可操作"),
    COURSE_FILE_ID_ILLEGAL(HttpStatus.BAD_REQUEST, "课程文件id无效"),
    COURSE_FILE_TYPE_ILLEGAL(HttpStatus.BAD_REQUEST, "操作的课程文件类型错误"),
    COURSE_FILE_ILLEGAL(HttpStatus.BAD_REQUEST, "课程文件已过期"),
    COURSE_FILE_NAME_ILLEGAL(HttpStatus.BAD_REQUEST, "文件名已存在"),
    EXAMINATION_PARAMETERS_ILLEGAL(HttpStatus.BAD_REQUEST, "参数格式错误"),

    TEACHING_TASK_ID_ERROR(HttpStatus.BAD_REQUEST, "教学任务id错误"),
    DOCUMENTATION_GET_BY_ID_ERROR(HttpStatus.BAD_REQUEST, "只能查询文件资料"),
    RESOURCE_GET_BY_ID_ERROR(HttpStatus.BAD_REQUEST, "只能查询教学资源"),
    COURSE_FILE_ROOT_ERROR(HttpStatus.BAD_REQUEST, "课程/教师根目录无法操作"),
    DOCUMENTATION_CANNOT_BE_FOLDER(HttpStatus.BAD_REQUEST, "文件资料无法作为文件夹"),
    RESOURCE_CANNOT_BE_FOLDER(HttpStatus.BAD_REQUEST, "教学资源无法作为文件夹"),
    ;

    private Integer code;
    private String message;

    TeacherErrorCodeEnum(HttpStatus httpStatus, String message) {
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
