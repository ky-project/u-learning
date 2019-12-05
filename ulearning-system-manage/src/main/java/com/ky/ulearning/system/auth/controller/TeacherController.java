package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.EncryptUtil;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.system.auth.service.TeacherService;
import com.ky.ulearning.system.common.enums.SystemErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
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
    public ResponseEntity login(TeacherEntity teacherEntity){
        if(teacherEntity == null
                || StringUtils.isEmpty(teacherEntity.getTeaNumber())
                || StringUtils.isEmpty(teacherEntity.getTeaPassword())){
            return ResponseEntity.badRequest().body((new JsonResult(SystemErrorCodeEnum.PARAMETER_EMPTY)));
        }
        TeacherEntity exists = teacherService.findByTeaNumber(teacherEntity.getTeaNumber());
        if(exists == null ){
            return ResponseEntity.badRequest().body((new JsonResult(SystemErrorCodeEnum.TEACHER_NOT_EXISTS)));
        }
        if(! exists.getTeaPassword().equals(EncryptUtil.encryptPassword(teacherEntity.getTeaPassword()))){
            return ResponseEntity.badRequest().body((new JsonResult(SystemErrorCodeEnum.PASSWORD_ERROR)));
        }
        return ResponseEntity.ok().body(new JsonResult<>(exists));
    }
}
