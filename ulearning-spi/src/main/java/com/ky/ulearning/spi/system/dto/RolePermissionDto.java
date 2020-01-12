package com.ky.ulearning.spi.system.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色权限dto
 *
 * @author luyuhao
 * @since 19/12/08 03:45
 */
@Data
public class RolePermissionDto extends BaseDto {

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色资源
     */
    @ApiModelProperty(value = "角色资源")
    private String roleSource;

    /**
     * 是否管理员角色
     */
    @ApiModelProperty(value = "是否管理员角色")
    private Boolean isAdmin;

    /**
     * 角色所拥有的所有权限
     */
    @ApiModelProperty(value = "角色所拥有的所有权限")
    List<PermissionEntity> permissionEntities;
}
