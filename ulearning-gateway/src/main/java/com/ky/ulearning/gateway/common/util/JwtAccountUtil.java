package com.ky.ulearning.gateway.common.util;

import cn.hutool.json.JSONObject;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

/**
 * 操作当前登录的用户信息
 *
 * @author luyuhao
 * @date 19/12/12 23:40
 */
public class JwtAccountUtil {

    private static JwtAccount getUserDetails() {
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
        return jwtAccount == null ? null : jwtAccount.getUsername();
    }

    /**
     * 获取系统用户id
     *
     * @return 系统用户id
     */
    public static Long getId() {
        JwtAccount jwtAccount = getUserDetails();
        return jwtAccount == null ? null : jwtAccount.getId();
    }

    /**
     * 获取系统用户角色, student or teacher
     *
     * @return 系统用户角色
     */
    public static String getSysRole() {
        JwtAccount jwtAccount = getUserDetails();
        return jwtAccount == null ? null : jwtAccount.getSysRole();
    }

    /**
     * 获取系统用户上次更新时间
     *
     * @return 系统用户上次更新时间
     */
    public static Date getUpdateTime() {
        JwtAccount jwtAccount = getUserDetails();
        return jwtAccount == null ? null : jwtAccount.getUpdateTime();
    }

    /**
     * 获取系统用户权限
     *
     * @return 系统用户权限
     */
    public static List<PermissionEntity> getPermissions() {
        JwtAccount jwtAccount = getUserDetails();
        return jwtAccount == null ? null : jwtAccount.getPermissions();
    }

}
