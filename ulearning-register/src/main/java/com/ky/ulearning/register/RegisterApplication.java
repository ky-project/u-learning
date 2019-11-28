package com.ky.ulearning.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.util.StringUtils;

/**
 * @author luyuhao
 * @date 19/11/28 14:33
 */
@SpringBootApplication
@EnableEurekaServer
public class RegisterApplication {
    public static void main(String[] args) {
        setLocation();
        SpringApplication.run(RegisterApplication.class, args);
    }

    private static void setLocation(){
        String path = System.getenv("ulearning");
        if(StringUtils.isEmpty(path)){
            path = "local";
        }
        System.setProperty("spring.config.location", "classpath:/config/" + path + "/");
    }
}
