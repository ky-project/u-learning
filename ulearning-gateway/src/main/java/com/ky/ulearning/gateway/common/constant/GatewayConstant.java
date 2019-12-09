package com.ky.ulearning.gateway.common.constant;

/**
 * @author luyuhao
 * @date 19/12/07 02:22
 */
public class GatewayConstant {
    /**
     * 验证码有效时间/分钟
     */
    public static final long LOGIN_CODE_EXPIRATION = 2L;

    /**
     * 登录后台管理系统：0
     */
    public static final int LOGIN_MANAGE_SYSTEM = 0;

    /**
     * 登录教师系统：1
     */
    public static final int LOGIN_TEACHER_SYSTEM = 1;

    /**
     * 登录学生端：2
     */
    public static final int LOGIN_STUDENT_SYSTEM = 2;

    public static final String SYS_ROLE_STUDENT = "student";

    public static final String SYS_ROLE_TEACHER = "teacher";

}
