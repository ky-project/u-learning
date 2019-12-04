package com.ky.ulearning.config;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author luyuhao
 * @date 19/11/28 17:19
 */
@EnableConfigServer
@SpringBootApplication
@EnableEurekaClient
public class ConfigApplication {
    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, "native");
        SpringApplication.run(ConfigApplication.class, args);
    }
}
