package com.ky.ulearning.spi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * {@link PermissionEntity}
 *
 * @author luyuhao
 * @date 19/12/15 03:25
 */
@ApiModel("权限dto对象")
@Data
public class PermissionDto implements Serializable {

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

    /**
     * id
     */
    @ApiModelProperty(value = "id", example = "1")
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
}
