package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.student.service.TeachingTaskExperimentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实验管理controller
 *
 * @author luyuhao
 * @since 20/03/05 00:55
 */
@Slf4j
@RestController
@Api(tags = "实验管理", description = "实验管理接口")
@RequestMapping(value = "/teachingTaskExperiment")
public class TeachingTaskExperimentController extends BaseController {

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;
}
