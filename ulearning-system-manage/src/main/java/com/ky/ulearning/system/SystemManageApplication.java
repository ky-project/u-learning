package com.ky.ulearning.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.util.StringUtils;

/**
 * @author luyuhao
 * @date 19/12/04 01:17
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class SystemManageApplication {
    public static void main(String[] args) {
        setLocation();
        SpringApplication.run(SystemManageApplication.class, args);
    }

    private static void setLocation(){
        String path = System.getenv("ulearning");
        if(StringUtils.isEmpty(path)){
            path = "local";
        }
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:/config/" + path + "/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, path);
    }
}
