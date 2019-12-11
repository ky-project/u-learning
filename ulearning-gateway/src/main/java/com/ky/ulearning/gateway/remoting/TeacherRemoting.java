package com.ky.ulearning.gateway.remoting;

import com.ky.ulearning.spi.system.dto.RolePermissionDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public interface TeacherRemoting {

    /**
     * 根据id查询教师角色权限
     *
     * @param id 教师id
     * @return 返回响应实体类
     */
    @GetMapping("/getRolePermissionById")
    List<RolePermissionDto> getRolePermissionById(@RequestParam("id") Long id);

    /**
     * 根据工号查询教师
     *
     * @param teaNumber 教师工号
     * @return 返回响应实体类
     */
    @GetMapping("/getByTeaNumber")
    TeacherEntity getByTeaNumber(@RequestParam("teaNumber") String teaNumber);

    /**
     * 更新教师信息
     *
     * @param teacherEntity 待更新的教师信息
     * @return 返回响应实体类
     */
    @GetMapping("/update")
    TeacherEntity update(@RequestParam Map<String, Object> teacherEntity);
}
