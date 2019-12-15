package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.system.auth.dao.RolePermissionDao;
import com.ky.ulearning.system.auth.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限service - 实现类
 *
 * @author luyuhao
 * @date 19/12/14 16:26
 */
@Service
@CacheConfig(cacheNames = {"role", "permission"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<PermissionEntity> getPermissionListByRoleId(List<Long> roleIdList) {
        return rolePermissionDao.getPermissionListByRoleId(roleIdList);
    }
}
