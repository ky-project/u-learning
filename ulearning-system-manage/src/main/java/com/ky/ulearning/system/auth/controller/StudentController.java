package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.system.auth.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @since 20/01/18 22:56
 */
@Slf4j
@RestController
@Api(tags = "学生管理", description = "学生管理接口")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
}
