package com.ky.ulearning.gateway.remoting;

import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.spi.common.dto.UserContext;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

/**
 * 调用system-manage的TeacherController接口
 *
 * @author luyuhao
 * @date 19/12/07 01:34
 */
@FeignClient("system-manage")
@RequestMapping(value = "/system-manage")
public interface SystemManageRemoting {

    /**
     * ------------------------ TeacherController ------------------------
     * 教师登录
     *
     * @param teaNumber 教师number
     * @return 登录的账号信息
     */
    @PostMapping("/teacher/login")
    JsonResult<UserContext> teacherLogin(@RequestParam("teaNumber") String teaNumber);

    /**
     * 根据id查询教师信息
     *
     * @param id 教师id
     * @return 教师
     */
    @GetMapping("/teacher/getById")
    JsonResult<TeacherEntity> teacherGetById(@RequestParam("id") Long id);

    /**
     * 更新教师信息
     *
     * @param teacherDto 待更新的教师对象
     * @return 更新的教师对象
     */
    @PostMapping("/teacher/update")
    JsonResult<TeacherDto> teacherUpdate(@RequestParam Map<String, Object> teacherDto);

    /**
     * ------------------------ StudentController ------------------------
     * 教师登录
     *
     * @param stuNumber 学生number
     * @return 登录的账号信息
     */
    @PostMapping("/student/login")
    JsonResult<UserContext> studentLogin(@RequestParam("stuNumber") String stuNumber);

    /**
     * 根据id查询学生信息
     *
     * @param id 学生id
     * @return 学生
     */
    @GetMapping("/student/getById")
    JsonResult<StudentEntity> studentGetById(@RequestParam("id") Long id);


    /**
     * 更新学生信息
     *
     * @param studentDto 待更新的学生对象
     * @return 提示信息
     */
    @PostMapping("/student/update")
    JsonResult studentUpdate(@RequestParam Map<String, Object> studentDto);
}
