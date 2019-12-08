package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色dao
 *
 * @author Darren
 * @date 2019/12/08 17:47
 */
@Mapper
@Repository
public interface RoleDao {
    /**
     * 插入记录
     *
     * @param role 待插入的角色
     */
    void insert(RoleEntity role);

    /**
     * 根据id查询角色
     *
     * @param id 角色id
     * @return 返回角色对象
     */
    RoleEntity getById(Long id);

    /**
     * 根据id更新角色信息
     * @param role 待更新的角色
     */
    void updateById(RoleEntity role);
}