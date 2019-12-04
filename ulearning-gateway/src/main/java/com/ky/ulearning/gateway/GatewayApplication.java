package com.ky.ulearning.gateway;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author luyuhao
 * @date 19/11/29 02:13
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApplication {
    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        SpringApplication.run(GatewayApplication.class, args);
    }
}
