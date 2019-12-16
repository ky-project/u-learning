package com.ky.ulearning.gateway.common.security;

import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.exceptions.exception.ServerErrorException;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.conversion.UserContextJwtAccountMapper;
import com.ky.ulearning.gateway.remoting.SystemManageRemoting;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.spi.common.dto.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author luyuhao
 * @date 19/12/10 00:40
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
        //TODO 学生登录
        UserContext student = null;
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
            //教师无任何角色时
            if (teacher.getRoles() == null) {
                throw new BadRequestException(GatewayErrorCodeEnum.TEACHER_HAS_NO_ROLE);
            }
            jwtAccount = userContextJwtAccountMapper.toDto(teacher);
            if (!CollectionUtils.isEmpty(teacher.getPermissions())) {
                jwtAccount.setAuthorities(teacher.getPermissions()
                        .stream()
                        .map(permissionEntity -> new SimpleGrantedAuthority(permissionEntity.getPermissionUrl()))
                        .collect(Collectors.toList()));
            }
        } else {
            //TODO
            jwtAccount = userContextJwtAccountMapper.toDto(teacher);
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
            JsonResult<UserContext> userContextJsonResult = systemManageRemoting.login(teaNumber);
            return Optional.ofNullable(userContextJsonResult)
                    .map(JsonResult::getData)
                    .orElse(null);
        }catch (Exception e) {
           throw new ServerErrorException();
        }
    }
}
