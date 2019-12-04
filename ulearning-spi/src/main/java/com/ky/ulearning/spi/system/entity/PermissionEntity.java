package com.ky.ulearning.spi.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 权限实体类
 *
 * @author Darren
 * @date 2019/12/05 01:42
 */
@Data
public class PermissionEntity implements Serializable {
    /**
     * 权限ID
     */
    private Long id;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 权限码
     */
    private String permissionSource;

    /**
     * 权限组
     */
    private String permissionGroup;

    /**
     * 是否有效
     */
    private Boolean valid;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;
}