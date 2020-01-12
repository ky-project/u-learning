package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherRoleEntity;
import com.ky.ulearning.system.auth.dao.RoleDao;
import com.ky.ulearning.system.auth.dao.TeacherRoleDao;
import com.ky.ulearning.system.auth.service.TeacherRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author luyuhao
 * @date 19/12/08 20:45
 */
@Service
@CacheConfig(cacheNames = {"teacher", "role"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class TeacherRoleServiceImpl implements TeacherRoleService {

    @Autowired
    private TeacherRoleDao teacherRoleDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<RoleEntity> getRoleByTeaId(Long teaId) {
        return teacherRoleDao.getRoleByTeaId(teaId);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void saveAssignedRole(Long teaId, String roleIds, String username) {
        //清除教师已有角色关联记录
        teacherRoleDao.deleteByTeaId(teaId);
        //若角色id集合是否为空，结束业务
        if(StringUtil.isEmpty(roleIds)){
            return;
        }
        //获取系统中所有角色
        List<RoleEntity> roleList = roleDao.list();
        //若系统角色为空，结束业务
        if(CollectionUtils.isEmpty(roleList)){
            return;
        }
        //获取所有角色id
        Set<Long> roleIdSet = roleList.stream()
                .map(RoleEntity::getId)
                .collect(Collectors.toSet());
        //获取角色id数组
        String[] roleIdArr = roleIds.split(",");
        List<TeacherRoleEntity> teacherRoleList = new ArrayList<>();
        //遍历角色id数组
        for (String roleId : roleIdArr) {
            //判断角色id是否存在
            if(!roleIdSet.contains(Long.parseLong(roleId))){
                continue;
            }
            TeacherRoleEntity teacherRoleEntity = new TeacherRoleEntity();
            teacherRoleEntity.setTeaId(teaId);
            teacherRoleEntity.setRoleId(Long.parseLong(roleId));
            teacherRoleEntity.setUpdateBy(username);
            teacherRoleEntity.setCreateBy(username);
            teacherRoleList.add(teacherRoleEntity);
        }
        //分配角色
        if(!CollectionUtils.isEmpty(teacherRoleList)){
            teacherRoleDao.batchInsert(teacherRoleList);
        }
    }
}
