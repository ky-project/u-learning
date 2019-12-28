package com.ky.ulearning.register;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author luyuhao
 * @date 19/11/28 14:33
 */
@SpringBootApplication(exclude = {
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class})
@EnableEurekaServer
public class RegisterApplication {
    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        SpringApplication.run(RegisterApplication.class, args);
    }
}
