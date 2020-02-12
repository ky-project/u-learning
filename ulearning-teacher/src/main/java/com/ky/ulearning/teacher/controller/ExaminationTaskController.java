package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.teacher.service.ExaminationTaskService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试任务controller
 *
 * @author luyuhao
 * @since 20/02/13 00:58
 */
@Slf4j
@RestController
@Api(tags = "测试任务管理", description = "测试任务管理接口")
@RequestMapping(value = "/examinationTask")
public class ExaminationTaskController extends BaseController {

    @Autowired
    private ExaminationTaskService examinationTaskService;

}
