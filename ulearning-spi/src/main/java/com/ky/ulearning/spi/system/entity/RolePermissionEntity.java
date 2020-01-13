package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限实体类
 *
 * @author luyuhao
 * @date 2019/12/08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePermissionEntity extends BaseEntity {

    /**
     * 用户权限ID
     */
    private Long permissionId;

    /**
     * 教师角色id
     */
    private Long roleId;
}