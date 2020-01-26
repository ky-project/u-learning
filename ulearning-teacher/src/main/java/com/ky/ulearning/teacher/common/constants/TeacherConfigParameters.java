package com.ky.ulearning.teacher.common.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @since 20/01/26 21:25
 */
@RefreshScope
@Component
@Getter
public class TeacherConfigParameters {

    @Value("${ulearning.term.preyears}")
    private Integer preYears;

    @Value("${ulearning.term.nextyears}")
    private Integer nextYears;
}
