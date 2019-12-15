package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.system.entity.RoleEntity;

import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/08 20:45
 */
public interface TeacherRoleService {

    /**
     * 根据教师id获取角色集合
     *
     * @param teaId 教师id
     * @return 返回角色集合
     */
    List<RoleEntity> getRoleByTeaId(Long teaId);
}
