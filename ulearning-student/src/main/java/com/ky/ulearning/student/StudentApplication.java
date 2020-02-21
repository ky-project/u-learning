package com.ky.ulearning.student;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author luyuhao
 * @since 20/02/21 20:26
 */
@SpringBootApplication(scanBasePackages = {
        "com.ky.ulearning.student",
        "com.ky.ulearning.common.core.component"})
@EnableEurekaClient
@EnableCaching
@EnableAsync
@EnableFeignClients
public class StudentApplication {

    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        SpringApplication.run(StudentApplication.class, args);
    }
}
