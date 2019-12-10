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
     * 系统学生角色名
     */
    public static final String SYS_ROLE_STUDENT = "student";

    /**
     * 系统教师角色名
     */
    public static final String SYS_ROLE_TEACHER = "teacher";

    /**
     * 存入cookie的token名
     */
    public static final String COOKIE_TOKEN = "token";

    /**
     * 存入cookie的refreshToken名
     */
    public static final String COOKIE_REFRESH_TOKEN = "refresh_token";

}
