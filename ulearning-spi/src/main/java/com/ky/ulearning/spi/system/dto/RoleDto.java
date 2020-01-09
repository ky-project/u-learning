package com.ky.ulearning.spi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.system.entity.RoleEntity}
 * 角色dto
 *
 * @author luyuhao
 * @since 20/01/10 01:25
 */
@Data
public class RoleDto implements Serializable {

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
}
