package com.ky.ulearning.teacher;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author luyuhao
 * @since 20/01/25 22:00
 */
@SpringBootApplication(scanBasePackages = {
        "com.ky.ulearning.teacher",
        "com.ky.ulearning.common.core.component"})
@EnableEurekaClient
@EnableCaching
@EnableAsync
@EnableFeignClients
public class TeacherApplication {
    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        SpringApplication.run(TeacherApplication.class, args);
    }
}
