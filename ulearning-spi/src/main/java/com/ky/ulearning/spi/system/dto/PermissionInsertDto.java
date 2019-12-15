package com.ky.ulearning.spi.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author luyuhao
 * @date 19/12/15 03:25
 */
@Data
public class PermissionInsertDto implements Serializable {
    /**
     * 权限名
     */
    @ApiModelProperty(value = "权限名", required = true)
    @NotNull(message = "权限名不可为空")
    private String permissionName;

    /**
     * 权限码
     */
    @ApiModelProperty(value = "权限码", required = true)
    @NotNull(message = "权限码不可为空")
    private String permissionSource;

    /**
     * 权限组
     */
    @ApiModelProperty(value = "权限组", required = true)
    @NotNull(message = "权限组不可为空")
    private String permissionGroup;

    /**
     * 权限url
     */
    @ApiModelProperty(value = "权限url", required = true)
    @NotNull(message = "权限url不可为空")
    private String permissionUrl;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String memo;

    /**
     * 创建者
     */
    @ApiModelProperty(hidden = true)
    private String createBy;

    /**
     * 更新者
     */
    @ApiModelProperty(hidden = true)
    private String updateBy;
}
