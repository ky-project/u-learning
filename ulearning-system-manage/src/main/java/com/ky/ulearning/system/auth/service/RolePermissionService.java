package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.system.entity.PermissionEntity;

import java.util.List;

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

}
