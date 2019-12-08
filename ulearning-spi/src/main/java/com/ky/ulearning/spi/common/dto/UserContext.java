package com.ky.ulearning.spi.common.dto;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherRoleEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author luyuhao
 * @date 2019/12/7 10:40
 */
@Data
@Accessors(chain = true)
public class UserContext implements Serializable {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private List<RoleEntity> roles;

    /**
     * 是否是管理员 0:否；1:是
     */
    private Integer manage;

    /**
     * 所拥有的权限
     */
    private List<PermissionEntity> permissions;

    /**
     * 更新时间
     */
    private Date updateTime;
}
