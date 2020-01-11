package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.RoleDto;
import com.ky.ulearning.spi.system.entity.RoleEntity;

/**
 * 角色service 接口类
 *
 * @author luyuhao
 * @since 19/12/08 18:31
 */
public interface RoleService {

    /**
     * 分页查询角色列表
     *
     * @param roleDto   筛选条件
     * @param pageParam 分页参数
     * @return 封装角色实体类的分页对象
     */
    PageBean<RoleEntity> pageRoleList(RoleDto roleDto, PageParam pageParam);

    /**
     * 添加角色
     *
     * @param roleDto 待添加的角色信息
     */
    void insert(RoleDto roleDto);

    /**
     * 删除角色
     *
     * @param id       待删除的角色id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);

    /**
     * 更新角色
     *
     * @param roleDto 待更新的角色对象
     */
    void update(RoleDto roleDto);
}
