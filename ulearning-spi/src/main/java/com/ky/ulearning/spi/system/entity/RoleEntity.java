package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体类
 *
 * @author luyuhao
 * @since 2019/12/08 17:46
 */
@ApiModel("角色实体类")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends BaseEntity {

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