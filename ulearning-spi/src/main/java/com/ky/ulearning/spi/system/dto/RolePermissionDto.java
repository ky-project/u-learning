package com.ky.ulearning.spi.system.dto;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 教师角色dto
 *
 * @author luyuhao
 * @date 19/12/08 03:45
 */
@Data
public class RolePermissionDto implements Serializable {

    /**
     * id
     */
    private Long id;
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

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色资源
     */
    private String roleSource;

    /**
     * 是否管理员角色
     */
    private Boolean isAdmin;

    /**
     * 教师角色所拥有的所有权限
     */
    List<PermissionEntity> permissionEntities;
}
