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
     * 存入cookie的token名
     */
    public static final String COOKIE_TOKEN = "token";

    /**
     * 存入cookie的refreshToken名
     */
    public static final String COOKIE_REFRESH_TOKEN = "refresh_token";

    /**
     * 请求头中的token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 静态文件
     */
    public static final String[] STATIC_SUFFIX = {".css", ".js", ".html", ".ico", ".svg", ".png"};
}
