package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.system.auth.dao.PermissionDao;
import com.ky.ulearning.system.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<String> getSources() {
        return permissionDao.getSources();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(PermissionEntity permission) {
        permissionDao.insert(permission);
    }

    @Override
    public List<PermissionEntity> getList() {
        return permissionDao.getList();
    }
}
