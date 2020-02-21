package com.ky.ulearning.common.core.constant;

/**
 * 服务统一通用常量
 *
 * @author luyuhao
 * @date 19/12/12 23:06
 */
public class MicroConstant {

    /**
     * 系统教师角色名
     */
    public static final String SYS_ROLE_TEACHER = "teacher";

    /**
     * 系统学生角色名
     */
    public static final String SYS_ROLE_STUDENT = "student";

    /**
     * 用户ip
     */
    public static final String USER_REQUEST_IP = "userIp";

    /**
     * 用户账号
     */
    public static final String USERNAME = "username";

    /**
     * 用户id
     */
    public static final String USER_ID = "userId";

    /**
     * 日志类型
     */
    public static final String[] LOG_TYPE = {"INFO", "ERROR"};

    /**
     * 教师表名
     */
    public static final String TEACHER_TABLE_NAME = "u_teacher";

    /**
     * 学生表名
     */
    public static final String STUDENT_TABLE_NAME = "u_student";

    /**
     * 课程试题表名
     */
    public static final String COURSE_QUESTION_TABLE_NAME = "u_course_question";

    /**
     * 教学任务实验表名
     */
    public static final String TEACHING_TASK_EXPERIMENT_TABLE_NAME = "u_teaching_task_experiment";

    /**
     * 教学任务通告表名
     */
    public static final String TEACHING_TASK_NOTICE_TABLE_NAME = "u_teaching_task_notice";

    /**
     * 日志历史表名
     */
    public static final String LOG_HISTORY_TABLE_NAME = "u_log_history";

    /**
     * 课程文件表名
     */
    public static final String COURSE_FILE_TABLE_NAME = "u_course_file";

    /**
     * 根目录名
     */
    public static final String ROOT_FOLDER = "\\";

    /**
     * 根目录的母节点id
     */
    public static final long ROOT_FOLDER_PARENTID = 0L;

    /**
     * 文件夹
     */
    public static final int FOLDER_TYPE = 2;

    /**
     * 文件
     */
    public static final int FILE_TYPE = 1;

}
