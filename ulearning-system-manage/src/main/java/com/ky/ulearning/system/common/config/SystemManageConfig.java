package com.ky.ulearning.system.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @date 19/12/11 23:19
 */
@Component
@Getter
public class SystemManageConfig {
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
}
