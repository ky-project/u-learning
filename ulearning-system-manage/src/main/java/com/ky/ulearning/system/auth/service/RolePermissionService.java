package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.system.entity.PermissionEntity;

import java.util.List;
import java.util.Map;

/**
 * 角色权限service - 接口类
 *
 * @author luyuhao
 * @date 19/12/14 16:25
 */
public interface RolePermissionService {

    /**
     * 根据角色id集合获取权限集合
     *
     * @param roleIdList 角色id集合
     * @return 返回权限对象集合
     */
    List<PermissionEntity> getPermissionListByRoleId(List<Long> roleIdList);

    /**
     * 根据角色id分组查询所拥有的所有权限
     *
     * @param roleId 角色id
     * @return 组名:权限list
     */
    Map<String, List<PermissionEntity>> getAssignedPermission(Long roleId);

    /**
     * 为角色分配权限
     *
     * @param roleId        角色id
     * @param permissionIds 权限id字符串，逗号分隔
     * @param username      创建者&更新者
     */
    void saveAssignedPermission(Long roleId, String permissionIds, String username);
}
