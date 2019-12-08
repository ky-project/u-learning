package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
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
public class PermissionEntity extends BaseEntity {

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
     * 权限url
     */
    private String permissionUrl;
}