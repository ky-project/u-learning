package com.ky.ulearning.gateway.remoting;

import com.ky.ulearning.spi.common.dto.UserContext;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 调用system-manage的TeacherController接口
 *
 * @author luyuhao
 * @date 19/12/07 01:34
 */
@FeignClient("system-manage")
@RequestMapping(value = "/system-manage/teacher")
public interface SystemManageRemoting {

    /**
     * ------------------------ TeacherController ------------------------
     * 教师登录
     *
     * @param teaNumber 教师number
     * @return 登录的账号信息
     */
    @PostMapping("/login")
    UserContext login(@RequestParam("teaNumber") String teaNumber);

    /**
     * 更新教师信息
     *
     * @param teacherEntity 待更新的教师信息
     * @return 教师信息
     */
    @PutMapping("/update")
    TeacherEntity update(@RequestParam Map<String, Object> teacherEntity);
}
