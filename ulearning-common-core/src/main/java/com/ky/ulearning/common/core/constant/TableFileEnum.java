package com.ky.ulearning.common.core.constant;

/**
 * @author luyuhao
 * @since 20/02/28 00:34
 */
public enum TableFileEnum {

    /**
     * 表-文件url字段映射enum
     */
    TEACHER_TABLE("u_teacher", "tea_photo"),
    STUDENT_TABLE("u_student", "stu_photo"),
    COURSE_QUESTION_TABLE("u_course_question", "question_URL"),
    TEACHING_TASK_EXPERIMENT_TABLE("u_teaching_task_experiment", "experiment_attachment"),
    TEACHING_TASK_NOTICE_TABLE("u_teaching_task_notice", "notice_attachment"),
    LOG_HISTORY_TABLE("u_log_history", "log_history_url"),
    COURSE_FILE_TABLE("u_course_file", "file_url"),
    EXPERIMENT_RESULT_TABLE("u_experiment_result", "experiment_URL"),
    NULL_TABLE("null", "null")
    ;

    private String tableName;

    private String urlColumn;

    TableFileEnum(String tableName, String urlColumn) {
        this.tableName = tableName;
        this.urlColumn = urlColumn;
    }

    public String getTableName() {
        return tableName;
    }

    public String getUrlColumn() {
        return urlColumn;
    }

    public static TableFileEnum getByTableName(String tableName) {
        TableFileEnum[] tableFileEnums = TableFileEnum.values();
        for (TableFileEnum tableFileEnum : tableFileEnums) {
            if(tableFileEnum.getTableName().equals(tableName)){
                return tableFileEnum;
            }
        }
        return NULL_TABLE;
    }
}
