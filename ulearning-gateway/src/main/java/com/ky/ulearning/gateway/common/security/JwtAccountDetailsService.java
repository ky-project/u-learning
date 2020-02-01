package com.ky.ulearning.gateway.common.security;

import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.conversion.UserContextJwtAccountMapper;
import com.ky.ulearning.gateway.remoting.SystemManageRemoting;
import com.ky.ulearning.spi.common.dto.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author luyuhao
 * @since 19/12/10 00:40
 */
@Service
public class JwtAccountDetailsService implements UserDetailsService {

    @Autowired
    private SystemManageRemoting systemManageRemoting;

    @Autowired
    private UserContextJwtAccountMapper userContextJwtAccountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //教师账号
        UserContext teacher = teacherLogin(username);
        //学生登录
        UserContext student = studentLogin(username);
        //账号都不存在时
        if (teacher == null && student == null) {
            throw new BadRequestException(GatewayErrorCodeEnum.USER_NOT_EXISTS);
        }
        //账号都存在时
        if (teacher != null && student != null) {
            throw new BadRequestException(GatewayErrorCodeEnum.ACCOUNT_ERROR);
        }
        JwtAccount jwtAccount;
        //判断是教师还是学生
        if (teacher != null) {
            jwtAccount = userContextJwtAccountMapper.toDto(teacher);
            if (!CollectionUtils.isEmpty(teacher.getPermissions())) {
                jwtAccount.setAuthorities(teacher.getPermissions()
                        .stream()
                        .map(permissionEntity -> new SimpleGrantedAuthority(permissionEntity.getPermissionUrl()))
                        .collect(Collectors.toList()));
            }
        } else {
            jwtAccount = userContextJwtAccountMapper.toDto(student);
            jwtAccount.setAuthorities(Collections.emptyList());
        }
        return jwtAccount;
    }

    /**
     * 登录教师账号并获取信息
     *
     * @param teaNumber 登录的教师账号 - 工号
     * @return 返回登录账号信息
     */
    private UserContext teacherLogin(String teaNumber) {
        //获取教师信息
        try {
            JsonResult<UserContext> userContextJsonResult = systemManageRemoting.teacherLogin(teaNumber);
            return Optional.ofNullable(userContextJsonResult)
                    .map(JsonResult::getData)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 登录学生账号并获取信息
     *
     * @param stuNumber 登录的学生账号 - 学号
     * @return 返回登录账号信息
     */
    private UserContext studentLogin(String stuNumber) {
        //获取教师信息
        try {
            JsonResult<UserContext> userContextJsonResult = systemManageRemoting.studentLogin(stuNumber);
            return Optional.ofNullable(userContextJsonResult)
                    .map(JsonResult::getData)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
