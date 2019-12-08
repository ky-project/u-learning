package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.system.dto.RolePermissionDto;

import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/08 20:45
 */
public interface TeacherRoleService {

    /**
     * 根据教师id获取角色和角色权限
     * @param teaId 教师id
     * @return 返回角色权限集合
     */
    List<RolePermissionDto> getRolePermissionByTeaId(Long teaId);
}
