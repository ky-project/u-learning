package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.system.dto.RolePermissionDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RolePermissionEntity;
import com.ky.ulearning.system.auth.dao.PermissionDao;
import com.ky.ulearning.system.auth.dao.RolePermissionDao;
import com.ky.ulearning.system.auth.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色权限service - 实现类
 *
 * @author luyuhao
 * @date 19/12/14 16:26
 */
@Service
@CacheConfig(cacheNames = {"role", "permission"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class RolePermissionServiceImpl extends BaseService implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<PermissionEntity> getPermissionListByRoleId(List<Long> roleIdList) {
        return rolePermissionDao.getPermissionListByRoleId(roleIdList);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, List<PermissionEntity>> getAssignedPermission(Long roleId) {
        List<PermissionEntity> permissionList = rolePermissionDao.getAssignedPermissionByRoleId(roleId);
        //空值判断
        if (null == permissionList) {
            return Collections.emptyMap();
        }
        Map<String, List<PermissionEntity>> groupList = new HashMap<>();
        //遍历权限集合
        for (PermissionEntity permission : permissionList) {
            //判断是否需要new集合
            if (!groupList.containsKey(permission.getPermissionGroup())) {
                groupList.put(permission.getPermissionGroup(), new ArrayList<>());
            }
            groupList.get(permission.getPermissionGroup()).add(permission);
        }
        return groupList;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void saveAssignedPermission(Long roleId, String permissionIds, String username) {
        //先删除角色所有已有权限
        rolePermissionDao.deleteByRoleId(roleId);
        //若权限id集合为空，结束业务
        if (StringUtil.isEmpty(permissionIds)) {
            return;
        }
        //获取所有权限集合
        List<PermissionEntity> permissionList = permissionDao.getList();
        //若权限为空，结束业务
        if (CollectionUtils.isEmpty(permissionList)) {
            return;
        }
        //获取系统中已有的权限id
        Set<Long> permissionIdSet = permissionList.stream()
                .map(PermissionEntity::getId)
                .collect(Collectors.toSet());
        //分配权限
        String[] permissionIdArr = permissionIds.split(",");
        List<RolePermissionEntity> permissionIdList = new ArrayList<>();
        //遍历权限id，将其转为long类型并存入集合中
        for (String permissionId : permissionIdArr) {
            //若插入的权限id不存在于系统中，则跳过
            if (!permissionIdSet.contains(Long.parseLong(permissionId))) {
                continue;
            }
            RolePermissionEntity permission = new RolePermissionEntity();
            permission.setRoleId(roleId);
            permission.setPermissionId(Long.parseLong(permissionId));
            permission.setCreateBy(username);
            permission.setUpdateBy(username);
            permissionIdList.add(permission);
        }
        //判断权限id集合不为空
        if (!CollectionUtils.isEmpty(permissionIdList)) {
            rolePermissionDao.batchInsert(permissionIdList);
        }
    }
}
