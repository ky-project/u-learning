package com.ky.ulearning.spi.system.dto;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import lombok.Data;

import java.util.List;

/**
 * 教师角色dto
 *
 * @author luyuhao
 * @date 19/12/08 03:45
 */
@Data
public class RolePermissionDto extends RoleEntity {
    /**
     * 教师角色所拥有的所有权限
     */
    List<PermissionEntity> permissionEntities;
}
