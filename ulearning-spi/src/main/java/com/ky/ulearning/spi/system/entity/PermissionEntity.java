package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 权限实体类
 *
 * @author luyuhao
 * @date 2019/12/05 01:42
 */
@ApiModel("权限实体类")
@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionEntity extends BaseEntity {

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