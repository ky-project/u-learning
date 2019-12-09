package com.ky.ulearning.gateway.remoting;

import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
     * 根据id查询教师角色权限
     *
     * @param id 教师id
     * @return 返回响应实体类
     */
    @GetMapping("teacher/getRolePermissionById")
    ResponseEntity getRolePermissionById(Long id);

    /**
     * 根据工号查询教师
     *
     * @param teaNumber 教师工号
     * @return 返回响应实体类
     */
    @GetMapping("teacher/getByTeaNumber")
    ResponseEntity getByTeaNumber(String teaNumber);

    /**
     * 更新教师信息
     *
     * @param teacherEntity 待更新的教师信息
     * @return 返回响应实体类
     */
    @GetMapping("teacher/update")
    public ResponseEntity update(TeacherEntity teacherEntity);
}
