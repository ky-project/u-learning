package com.ky.ulearning.spi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {@link com.ky.ulearning.spi.system.entity.PermissionEntity}
 * 权限数组vo
 *
 * @author luyuhao
 * @since 20/02/17 14:00
 */
@Data
@ApiModel("权限数组vo")
public class PermissionArrayVo {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String key;

    /**
     * 权限名
     */
    @ApiModelProperty("权限名")
    private String label;
}
