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

    /**
     * 分配教师角色
     *
     * @param teaId    教师id
     * @param roleIds  角色id，逗号分隔
     * @param username 更新者&创建者名
     */
    void saveAssignedRole(Long teaId, String roleIds, String username);
}
