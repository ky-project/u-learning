package com.ky.ulearning.register.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyuhao
 * @date 19/11/28 16:44
 */
//@Configuration
public class DefaultConfig {
    @Value("${jwt.server}")
    private String server;

    @Bean("exp")
    public Boolean getBoolean(){
        System.err.println("jwt.server=" + server);
        return true;
    }
}
