package com.ky.ulearning.gateway.common.utils;

import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 操作当前登录的用户信息
 *
 * @author luyuhao
 * @date 19/12/12 23:40
 */
public class JwtAccountUtil {

    public static JwtAccount getUserDetails() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
        return (JwtAccount) userDetails;
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getUsername() {
        JwtAccount jwtAccount = getUserDetails();
        return Optional.ofNullable(jwtAccount)
                .map(JwtAccount::getUsername)
                .orElse(null);
    }

    /**
     * 获取系统用户id
     *
     * @return 系统用户id
     */
    public static Long getId() {
        JwtAccount jwtAccount = getUserDetails();
        return Optional.ofNullable(jwtAccount)
                .map(JwtAccount::getId)
                .orElse(null);
    }

    /**
     * 获取系统用户角色, student or teacher
     *
     * @return 系统用户角色
     */
    public static String getSysRole() {
        JwtAccount jwtAccount = getUserDetails();
        return Optional.ofNullable(jwtAccount)
                .map(JwtAccount::getSysRole)
                .orElse(null);
    }

    /**
     * 获取系统用户上次敏感信息更新时间
     *
     * @return 系统用户上次敏感信息更新时间
     */
    public static Date getPwdUpdateTime() {
        JwtAccount jwtAccount = getUserDetails();
        return Optional.ofNullable(jwtAccount)
                .map(JwtAccount::getPwdUpdateTime)
                .orElse(null);
    }

    /**
     * 获取系统用户权限
     *
     * @return 系统用户权限
     */
    public static List<PermissionEntity> getPermissions() {
        JwtAccount jwtAccount = getUserDetails();
        return Optional.ofNullable(jwtAccount)
                .map(JwtAccount::getPermissions)
                .orElse(Collections.emptyList());
    }

    /**
     * 获取系统用户角色集合
     *
     * @return 系统用户角色集合
     */
    public static List<RoleEntity> getRoles() {
        JwtAccount jwtAccount = getUserDetails();
        return Optional.ofNullable(jwtAccount)
                .map(JwtAccount::getRoles)
                .orElse(Collections.emptyList());
    }

}
