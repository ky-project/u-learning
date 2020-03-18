package com.ky.ulearning.common.core.constant;

/**
 * 业务通用常量
 *
 * @author luyuhao
 * @since 2020/03/12 23:17
 */
public class CommonConstant {

    /**
     * 测试任务状态，1：未发布 2：未开始 3：进行中 4：已结束
     */
    public static final Integer[] EXAMINATION_STATE = {1, 2, 3, 4};

    /**
     * 答案分割符-split使用
     */
    public static final String COURSE_QUESTION_SEPARATE = "\\|#\\|";

    /**
     * 答案分割符-判断使用
     */
    public static final String COURSE_QUESTION_SEPARATE_JUDGE = "|#|";

    public static final String DRUID_STAT_WEB_URI = "/weburi.json";

}
