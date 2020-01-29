package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.teacher.service.StudentTeachingTaskService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生选课controller
 *
 * @author luyuhao
 * @since 20/01/30 00:35
 */
@Slf4j
@RestController
@Api(tags = "学生选课管理", description = "学生选课管理接口")
@RequestMapping(value = "/studentTeachingTask")
public class StudentTeachingTaskController extends BaseController {

    @Autowired
    private StudentTeachingTaskService studentTeachingTaskService;

}
