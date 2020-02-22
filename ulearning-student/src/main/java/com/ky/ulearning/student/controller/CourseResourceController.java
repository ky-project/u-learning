package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.student.service.CourseResourceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @since 20/02/22 20:39
 */
@Slf4j
@RestController
@Api(tags = "教学资源管理", description = "教学资源管理接口")
@RequestMapping(value = "/courseResource")
public class CourseResourceController extends BaseController {

    @Autowired
    private CourseResourceService courseResourceService;
}
