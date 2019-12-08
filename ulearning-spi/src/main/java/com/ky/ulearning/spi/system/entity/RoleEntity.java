package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import lombok.Data;

/**
 * 角色实体类
 *
 * @author Darren
 * @date 2019/12/08 17:46
 */
@Data
public class RoleEntity extends BaseEntity {
    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色资源
     */
    private String roleSource;

    /**
     * 是否管理员角色
     */
    private Boolean isAdmin;

}