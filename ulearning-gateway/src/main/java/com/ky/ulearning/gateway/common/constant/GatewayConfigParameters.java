package com.ky.ulearning.gateway.common.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @date 19/12/09 03:03
 */
@Component
@Getter
@RefreshScope
public class GatewayConfigParameters {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.refresh-token}")
    private Long refreshExpiration;

    @Value("${jwt.header-refresh-token}")
    private String refreshTokenHeader;

    @Value("${jwt.token}")
    private Long expiration;

    @Value("${jwt.header-token}")
    private String tokenHeader;

    @Value("${ulearning.admin-patterns}")
    private String[] adminPatterns;

    @Value("${ulearning.teacher-patterns}")
    private String[] teacherPatterns;

    @Value("${ulearning.student-patterns}")
    private String[] studentPatterns;

    /** 无需进行权限校验的路径 */
    @Value("${ulearning.permission-release-patterns}")
    private String[] permissionReleasePatterns;

    /** 无需登录验证的路径 */
    @Value("${ulearning.authenticate-release-patterns}")
    private String[] authenticateReleasePatterns;
}
