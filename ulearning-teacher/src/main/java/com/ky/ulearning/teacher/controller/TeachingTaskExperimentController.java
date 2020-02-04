package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.teacher.service.TeachingTaskExperimentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实验controller
 *
 * @author luyuhao
 * @since 20/02/04 21:05
 */
@Slf4j
@RestController
@Api(tags = "实验管理", description = "实验管理接口")
@RequestMapping(value = "/teachingTaskExperiment")
public class TeachingTaskExperimentController {

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;
}
