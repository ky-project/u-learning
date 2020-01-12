package com.ky.ulearning.spi.system.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {@link PermissionEntity}
 *
 * @author luyuhao
 * @date 19/12/15 03:25
 */
@ApiModel("权限dto对象")
@Data
public class PermissionDto extends BaseDto {

    /**
     * 权限名
     */
    @ApiModelProperty("权限名")
    private String permissionName;

    /**
     * 权限码
     */
    @ApiModelProperty("权限码")
    private String permissionSource;

    /**
     * 权限组
     */
    @ApiModelProperty("权限组")
    private String permissionGroup;

    /**
     * 权限url
     */
    @ApiModelProperty("权限url")
    private String permissionUrl;
}
