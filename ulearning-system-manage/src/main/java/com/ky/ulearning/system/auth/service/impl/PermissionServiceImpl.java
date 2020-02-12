package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.PermissionDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.system.auth.dao.PermissionDao;
import com.ky.ulearning.system.auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 权限表service-实现类
 *
 * @author luyuhao
 * @date 19/12/08 14:21
 */
@Service
@CacheConfig(cacheNames = "permission")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class PermissionServiceImpl extends BaseService implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<String> getSources() {
        return permissionDao.getSources();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(PermissionDto permissionDto) {
        //判断权限码是否已经存在
        PermissionEntity permissionSourceExists = permissionDao.getByPermissionSource(permissionDto.getPermissionSource());
        if (permissionSourceExists != null) {
            throw new EntityExistException("权限码");
        }
        //判断权限url是否已经存在
        PermissionEntity permissionUrlExists = permissionDao.getByPermissionUrl(permissionDto.getPermissionUrl());
        if (permissionUrlExists != null) {
            throw new EntityExistException("权限url");
        }
        permissionDao.insert(permissionDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<PermissionEntity> getList() {
        return permissionDao.getList();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String username) {
        permissionDao.updateValidById(id, 0, username);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(PermissionDto permissionDto) {
        //判断权限码是否已经存在
        if (!StringUtil.isEmpty(permissionDto.getPermissionSource())) {
            PermissionEntity permissionSourceExists = permissionDao.getByPermissionSource(permissionDto.getPermissionSource());
            if (permissionSourceExists != null && !permissionDto.getId().equals(permissionSourceExists.getId())) {
                throw new EntityExistException("权限码");
            }
        }
        //判断权限url是否已经存在
        if (!StringUtil.isEmpty(permissionDto.getPermissionUrl())) {
            PermissionEntity permissionUrlExists = permissionDao.getByPermissionUrl(permissionDto.getPermissionUrl());
            if (permissionUrlExists != null && !permissionDto.getId().equals(permissionUrlExists.getId())) {
                throw new EntityExistException("权限url");
            }
        }

        permissionDao.update(permissionDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public PageBean<PermissionEntity> pagePermissionList(PermissionDto permission, PageParam pageParam) {
        List<PermissionEntity> permissionList = permissionDao.listPage(permission, pageParam);

        PageBean<PermissionEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(permissionDao.countListPage(permission))
                //设置查询结果
                .setContent(permissionList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<String> getAllGroup() {
        return permissionDao.getAllGroup();
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<String> getAllUrl() {
        return permissionDao.getAllUrl();
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, List<PermissionEntity>> groupList() {
        List<PermissionEntity> permissionList = permissionDao.getList();
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
}
