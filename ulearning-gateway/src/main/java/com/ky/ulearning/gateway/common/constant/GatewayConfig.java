package com.ky.ulearning.gateway.common.constant;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @date 19/12/09 03:03
 */
@Component
@Getter
public class GatewayConfig {

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

}
