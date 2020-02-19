package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 根据角色id查询角色权限
     *
     * @param roleIdList 角色id集合
     * @return 返回角色权限对象集合
     */
    List<PermissionEntity> getPermissionListByRoleId(@Param("roleIdList") List<Long> roleIdList);


    /**
     * 根据角色id查询角色权限
     *
     * @param roleId 角色id
     * @return 返回角色权限对象集合
     */
    List<KeyLabelVo> getAssignedPermissionByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色id删除关联记录
     *
     * @param roleId 角色id
     */
    void deleteByRoleId(Long roleId);

    /**
     * 批量插入角色权限关联记录
     *
     * @param permissionList 关联对象集合
     */
    void batchInsert(@Param("permissionList") List<RolePermissionEntity> permissionList);
}