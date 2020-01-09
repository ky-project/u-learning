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
}
