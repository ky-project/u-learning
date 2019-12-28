package com.ky.ulearning.spi.common.dto;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/15 01:24
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
     * 系统角色：教师， 学生
     */
    private String sysRole;

    /**
     * 教师角色
     */
    private List<RoleEntity> roles;

    /**
     * 所拥有的权限
     */
    private List<PermissionEntity> permissions;

    /**
     * 更新时间
     */
    private Date updateTime;
}
