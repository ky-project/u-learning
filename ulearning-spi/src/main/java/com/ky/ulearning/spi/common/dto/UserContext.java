package com.ky.ulearning.spi.common.dto;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyuhao
 * @date 2019/12/7 10:40
 */
@Data
@Accessors(chain = true)
public class UserContext implements Serializable {
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
    private String role;

    /**
     * 是否是管理员 0:否；1:是
     */
    private Integer manage;

    /**
     * 所拥有的权限
     */
    private List<PermissionEntity> permissionEntities;
}
