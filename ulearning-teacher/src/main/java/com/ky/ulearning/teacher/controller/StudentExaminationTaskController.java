package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.teacher.service.StudentExaminationTaskService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @since 2020/03/16 00:18
 */
@Slf4j
@RestController
@Api(tags = "学生测试管理", description = "学生测试管理接口")
@RequestMapping(value = "/studentExaminationTask")
public class StudentExaminationTaskController {

    @Autowired
    private StudentExaminationTaskService studentExaminationTaskService;


}
