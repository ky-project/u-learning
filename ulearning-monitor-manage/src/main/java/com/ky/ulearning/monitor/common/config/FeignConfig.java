package com.ky.ulearning.monitor.common.config;

import com.ky.ulearning.common.core.exceptions.handler.GlobalFeignErrorDecoder;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign配置类
 *
 * @author luyuhao
 * @date 19/12/18 22:27
 */
@Slf4j
@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new GlobalFeignErrorDecoder();
    }
}
