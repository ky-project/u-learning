package com.ky.ulearning.system.sys.controller;

import com.ky.ulearning.system.sys.service.TeachingTaskService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教学任务管理controller
 *
 * @author luyuhao
 * @since 20/01/16 00:32
 */
@Slf4j
@RestController
@Api(tags = "教学任务管理", description = "教学任务管理接口")
@RequestMapping(value = "/teachingTask")
public class TeachingTaskController {

    @Autowired
    private TeachingTaskService teachingTaskService;
}
