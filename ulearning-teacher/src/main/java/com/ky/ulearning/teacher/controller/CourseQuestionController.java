package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.teacher.service.CourseQuestionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 试题controller
 *
 * @author luyuhao
 * @since 20/02/03 19:54
 */
@Slf4j
@RestController
@Api(tags = "试题管理", description = "试题管理接口")
@RequestMapping(value = "/courseQuestion")
public class CourseQuestionController extends BaseController {

    @Autowired
    private CourseQuestionService courseQuestionService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;


}
