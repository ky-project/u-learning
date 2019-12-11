package com.ky.ulearning.gateway.common.security;

import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.conversion.RolePermissionMapper;
import com.ky.ulearning.gateway.remoting.TeacherRemoting;
import com.ky.ulearning.spi.system.dto.RolePermissionDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luyuhao
 * @date 19/12/10 00:40
 */
@Service
public class JwtAccountDetailsService implements UserDetailsService {

    @Autowired
    private TeacherRemoting teacherRemoting;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //教师账号
        JwtAccount teacher;
        try {
            teacher = teacherLogin(username);
        } catch (Exception e){
            teacher = null;
        }
        JwtAccount student;
        try {
            //TODO 学生账号
            student = null;
        }catch (Exception e){
            student = null;
        }
        //账号都不存在时
        if(teacher == null && student == null){
            throw new BadRequestException(GatewayErrorCodeEnum.USER_NOT_EXISTS);
        }
        //账号都存在时
        if(teacher != null && student != null){
            throw new BadRequestException(GatewayErrorCodeEnum.ACCOUNT_ERROR);
        }
        JwtAccount jwtAccount;
        //判断是教师还是学生
        if(teacher != null) {
            //教师无任何角色时
            if(teacher.getRoles() == null){
                throw new BadRequestException(GatewayErrorCodeEnum.TEACHER_HAS_NO_ROLE);
            }
            teacher.setAuthorities(teacher.getPermissions()
                    .stream()
                    .map(permissionEntity -> new SimpleGrantedAuthority(permissionEntity.getPermissionUrl()))
                    .collect(Collectors.toList()));
            jwtAccount = teacher;
        } else{
            //TODO
            jwtAccount = student;
        }
        return jwtAccount;
    }

    /**
     * 登录教师账号并获取信息
     *
     * @param username 登录账号
     * @return 返回登录账号信息
     */
    private JwtAccount teacherLogin(String username){
        //获取教师信息
        TeacherEntity teacher = teacherRemoting.getByTeaNumber(username);
        if(teacher == null){
            return null;
        }
        JwtAccount jwtAccount = new JwtAccount()
                .setId(teacher.getId())
                .setSysRole(GatewayConstant.SYS_ROLE_TEACHER)
                .setUsername(teacher.getTeaNumber())
                .setPassword(teacher.getTeaPassword())
                .setUpdateTime(teacher.getUpdateTime());

        //获取该教师的角色权限
        List<RolePermissionDto> rolePermissionDtoList = teacherRemoting.getRolePermissionById(teacher.getId());
        //若有权限，则抽取赋值
        if (rolePermissionDtoList != null) {
            //抽取角色和权限
            List<RoleEntity> roleList = new ArrayList<>();
            List<PermissionEntity> permissionList = new ArrayList<>();
            //遍历获取到的角色权限集合
            for (RolePermissionDto rolePermissionDto : rolePermissionDtoList) {
                //抽取角色
                roleList.add(rolePermissionMapper.toEntity(rolePermissionDto));
                //抽取权限
                if(rolePermissionDto.getPermissionEntities() != null){
                    permissionList.addAll(rolePermissionDto.getPermissionEntities());
                }
            }
            jwtAccount.setRoles(roleList);
            jwtAccount.setPermissions(permissionList);
        }
        return jwtAccount;
    }
}
