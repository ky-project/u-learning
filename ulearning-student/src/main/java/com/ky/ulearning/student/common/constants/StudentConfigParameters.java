package com.ky.ulearning.student.common.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @since 2020/03/27 00:51
 */
@Component
@Getter
public class StudentConfigParameters {

    @Value("${spring.application.name}")
    private String appName;
}
