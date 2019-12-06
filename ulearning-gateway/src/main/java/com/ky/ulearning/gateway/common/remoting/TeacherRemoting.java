package com.ky.ulearning.gateway.common.remoting;

import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 调用system-manage的TeacherController接口
 *
 * @author luyuhao
 * @date 19/12/07 01:34
 */
@FeignClient("ulearning-system-manage")
public interface TeacherRemoting {

    /**
     * 教师登录接口
     * @param teacherEntity 教师实体类
     * @return 返回响应实体类
     */
    @PostMapping("teacher/login")
    ResponseEntity login(TeacherEntity teacherEntity);
}
