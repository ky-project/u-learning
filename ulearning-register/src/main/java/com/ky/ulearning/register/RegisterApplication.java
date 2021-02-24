package com.ky.ulearning.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.util.StringUtils;

/**
 * @author luyuhao
 * @date 19/11/28 14:33
 */
@SpringBootApplication
@EnableEurekaServer
public class RegisterApplication {

    private static final Logger log = LoggerFactory.getLogger(RegisterApplication.class);

    public static void main(String[] args) {
        adjust();
        SpringApplication.run(RegisterApplication.class, args);
    }

    private static void adjust() {
        String path = System.getenv("ulearning");
        String ulearningSecret = System.getenv("ulearningSecret");
        if (StringUtils.isEmpty(path)) {
            path = "local";
        }
        log.info("当前环境为 {}", path);
        log.info("系统密钥为 {}", ulearningSecret);
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:/config/" + path + "/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, path);
        System.setProperty("jasypt.encryptor.password", ulearningSecret);
    }
}
