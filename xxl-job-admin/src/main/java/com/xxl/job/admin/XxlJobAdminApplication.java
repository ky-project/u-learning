package com.xxl.job.admin;

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

    public static void main(String[] args) {
        adjust();
        SpringApplication.run(XxlJobAdminApplication.class, args);
    }

    public static void adjust() {
        String path = System.getenv("ulearning");
        if (StringUtils.isEmpty(path)) {
            path = "local";
        }
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:/config/" + path + "/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, path);
    }
}