package com.ky.ulearning.spi.system.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.system.entity.RoleEntity}
 * 角色dto
 *
 * @author luyuhao
 * @since 20/01/10 01:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends BaseDto {

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     * 角色资源
     */
    @ApiModelProperty("角色资源")
    private String roleSource;

    /**
     * 是否管理员角色
     */
    @ApiModelProperty("是否管理员角色")
    private Boolean isAdmin;

}
