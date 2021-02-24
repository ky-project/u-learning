package com.xxl.job.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.util.StringUtils;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@EnableEurekaClient
@SpringBootApplication
public class XxlJobAdminApplication {

    private static final Logger log = LoggerFactory.getLogger(XxlJobAdminApplication.class);

    public static void main(String[] args) {
        adjust();
        SpringApplication.run(XxlJobAdminApplication.class, args);
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