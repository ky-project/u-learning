package com.ky.ulearning.gateway.common.security;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 登录账号信息
 *
 * @author luyuhao
 * @since 19/12/06 21:30
 */
@Data
@Accessors(chain = true)
public class JwtAccount implements UserDetails {

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
     * 密码更新时间
     */
    private Date pwdUpdateTime;

    /**
     * 权限集合
     */
    private Collection<GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
