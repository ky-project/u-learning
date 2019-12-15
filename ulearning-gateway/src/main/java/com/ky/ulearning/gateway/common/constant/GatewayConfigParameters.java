package com.ky.ulearning.gateway.common.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @date 19/12/09 03:03
 */
@Component
@Getter
public class GatewayConfigParameters {

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

    @Value("${swagger.enabled}")
    private Boolean swaggerEnabled;

    @Value("${ulearning.admin-patterns}")
    private String[] adminPatterns;

    @Value("${ulearning.teacher-patterns}")
    private String[] teacherPatterns;

    @Value("${ulearning.student-patterns}")
    private String[] studentPatterns;

    @Value("${ulearning.release-paths}")
    private String[] releasePaths;
}
