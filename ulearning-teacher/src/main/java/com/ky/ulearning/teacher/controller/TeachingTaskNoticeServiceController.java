package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.teacher.service.TeachingTaskNoticeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教学任务通告controller
 *
 * @author luyuhao
 * @since 20/01/30 23:39
 */
@Slf4j
@RestController
@Api(tags = "通告管理", description = "通告管理接口")
@RequestMapping(value = "/teachingTaskNotice")
public class TeachingTaskNoticeServiceController extends BaseController {

    @Autowired
    private TeachingTaskNoticeService teachingTaskNoticeService;
}
