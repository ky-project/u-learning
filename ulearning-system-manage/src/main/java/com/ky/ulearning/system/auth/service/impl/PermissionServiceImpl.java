package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.spi.system.dto.PermissionInsertDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.system.auth.dao.PermissionDao;
import com.ky.ulearning.system.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限表service-实现类
 *
 * @author luyuhao
 * @date 19/12/08 14:21
 */
@Service
@CacheConfig(cacheNames = "permission")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<String> getSources() {
        return permissionDao.getSources();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(PermissionInsertDto permissionInsertDto) {
        permissionDao.insert(permissionInsertDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<PermissionEntity> getList() {
        return permissionDao.getList();
    }
}
