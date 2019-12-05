package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.system.auth.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教师控制器
 *
 * @author luyuhao
 * @date 19/12/05 01:57
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
   private TeacherService teacherService;

    @Log("登录后台管理系统")
    @PostMapping("/login")
    public JsonResult login(){
        return null;
    }
}
