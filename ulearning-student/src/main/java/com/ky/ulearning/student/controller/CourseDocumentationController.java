package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.student.service.CourseDocumentationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @since 20/02/22 20:35
 */
@Slf4j
@RestController
@Api(tags = "文件资料管理", description = "文件资料管理接口")
@RequestMapping(value = "/courseDocumentation")
public class CourseDocumentationController extends BaseController {

    @Autowired
    private CourseDocumentationService courseDocumentationService;
}
