package com.ky.ulearning.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.util.StringUtils;

/**
 * @author luyuhao
 * @date 19/11/29 02:13
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayApplication {
    public static void main(String[] args) {
        setLocation();
        SpringApplication.run(GatewayApplication.class, args);
    }

    private static void setLocation(){
        String path = System.getenv("ulearning");
        if(StringUtils.isEmpty(path)){
            path = "local";
        }
        System.setProperty("spring.config.location", "classpath:/config/" + path + "/");
    }
}
