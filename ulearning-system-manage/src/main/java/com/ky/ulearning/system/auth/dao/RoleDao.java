package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.RoleDto;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色dao
 *
 * @author Darren
 * @since 2019/12/08 17:47
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
     *
     * @param role 待更新的角色
     */
    void updateById(RoleEntity role);

    /**
     * 分页查询角色列表
     *
     * @param roleDto   筛选条件
     * @param pageParam 分页参数
     * @return 封装角色实体类的分页对象
     */
    List<RoleEntity> listPage(@Param("roleDto") RoleDto roleDto, @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询总记录数
     *
     * @param roleDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("roleDto") RoleDto roleDto);
}