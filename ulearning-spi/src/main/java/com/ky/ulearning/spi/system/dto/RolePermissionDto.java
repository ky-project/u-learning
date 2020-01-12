package com.ky.ulearning.spi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色权限dto
 *
 * @author luyuhao
 * @since 19/12/08 03:45
 */
@Data
public class RolePermissionDto implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 是否有效
     */
    @ApiModelProperty(hidden = true)
    private Boolean valid;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String memo;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建者
     */
    @ApiModelProperty(hidden = true)
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新者
     */
    @ApiModelProperty(hidden = true)
    private String updateBy;

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
