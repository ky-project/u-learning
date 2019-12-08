package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.system.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色权限dao
 *
 * @author Darren
 * @date 2019/12/08 18:00
 */
@Mapper
@Repository
public interface RolePermissionDao {
    /**
     * 根据id删除角色权限关联
     *
     * @param id 角色权限id
     */
    void deleteById(Long id);

    /**
     * 插入角色权限记录
     *
     * @param rolePermission 待插入的角色权限对象
     */
    void insert(RolePermissionEntity rolePermission);

    /**
     * 根据id查询角色权限
     *
     * @param id 角色权限id
     * @return 返回角色权限对象
     */
    RolePermissionEntity getById(Long id);

    /**
     * 根据id更新角色权限
     *
     * @param rolePermission 待更新的角色权限
     */
    void updateById(RolePermissionEntity rolePermission);
}