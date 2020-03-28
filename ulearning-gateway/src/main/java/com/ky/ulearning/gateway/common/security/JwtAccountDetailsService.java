package com.ky.ulearning.gateway.common.security;

import com.ky.ulearning.common.core.component.component.RedisClientWrapper;
import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.conversion.UserContextJwtAccountMapper;
import com.ky.ulearning.gateway.common.utils.JwtAccountUtil;
import com.ky.ulearning.gateway.remoting.SystemManageRemoting;
import com.ky.ulearning.spi.common.dto.UserContext;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Objects;
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

    @Autowired
    private RedisClientWrapper redisClientWrapper;

    /**
     * 1. 先从缓存中获取
     * 2. 缓存未命中再去调用接口
     * <p>
     * 任何修改学生、教师、教师角色、角色、角色权限和权限表的操作都会清空该缓存
     */
    @Override
    public UserDetails loadUserByUsername(String usernameCom) throws UsernameNotFoundException {
        String username = usernameCom.substring(0, usernameCom.lastIndexOf(CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE));
        int loginType = Integer.parseInt(usernameCom.substring(usernameCom.lastIndexOf(CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE) + CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE.length()));

        //角色提取
        String sysRole = JwtAccountUtil.getSysRoleByLoginType(loginType);
        //从缓存中获取
        Object result = redisClientWrapper.get(MicroConstant.LOGIN_USER_REDIS_PREFIX + username + "::" + sysRole);
        if (StringUtil.isNotEmpty(result)) {
            return JsonUtil.parseObject(result.toString(), JwtAccount.class);
        }
        //后台 or 教师 or 学生登录
        JwtAccount jwtAccount;
        switch (loginType) {
            //后台登录
            case MicroConstant.LOGIN_TYPE_ADMIN:
                UserContext admin = teacherLogin(username);
                if (Objects.isNull(admin)) {
                    throw new BadRequestException(GatewayErrorCodeEnum.USER_NOT_EXISTS);
                }
                jwtAccount = userContextJwtAccountMapper.toDto(admin);
                //判断是否有管理员权限
                if (!jwtAccount.getRoles().stream()
                        .map(RoleEntity::getIsAdmin)
                        .collect(Collectors.toList())
                        .contains(true)) {
                    throw new BadRequestException(GatewayErrorCodeEnum.NOT_ADMIN);
                }
                if (!CollectionUtils.isEmpty(admin.getPermissions())) {
                    jwtAccount.setAuthorities(admin.getPermissions()
                            .stream()
                            .map(permissionEntity -> new SimpleGrantedAuthority(permissionEntity.getPermissionUrl()))
                            .collect(Collectors.toList()));
                }
                break;
            //教师登录
            case MicroConstant.LOGIN_TYPE_TEACHER:
                UserContext teacher = teacherLogin(username);
                if (Objects.isNull(teacher)) {
                    throw new BadRequestException(GatewayErrorCodeEnum.USER_NOT_EXISTS);
                }
                jwtAccount = userContextJwtAccountMapper.toDto(teacher);
                if (!CollectionUtils.isEmpty(teacher.getPermissions())) {
                    jwtAccount.setAuthorities(teacher.getPermissions()
                            .stream()
                            .map(permissionEntity -> new SimpleGrantedAuthority(permissionEntity.getPermissionUrl()))
                            .collect(Collectors.toList()));
                }
                break;
            //学生登录
            case MicroConstant.LOGIN_TYPE_STUDENT:
                UserContext student = studentLogin(username);
                if (Objects.isNull(student)) {
                    throw new BadRequestException(GatewayErrorCodeEnum.USER_NOT_EXISTS);
                }
                jwtAccount = userContextJwtAccountMapper.toDto(student);
                jwtAccount.setAuthorities(Collections.emptyList());
                break;
            //未知登录
            default:
                throw new BadRequestException(GatewayErrorCodeEnum.LOGIN_TYPE_ERROR);
        }
        jwtAccount.setLoginType(loginType);
        //设置进缓存
        redisClientWrapper.set(MicroConstant.LOGIN_USER_REDIS_PREFIX + username + "::" + jwtAccount.getSysRole(), JsonUtil.toJsonString(jwtAccount), MicroConstant.LOGIN_USER_REDIS_EXPIRE);
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
