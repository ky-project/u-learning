package com.ky.ulearning.monitor;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author luyuhao
 * @date 19/12/05 02:07
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class MonitorApplication {
    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        SpringApplication.run(MonitorApplication.class, args);
    }
}
