package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
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
        permissionDao.update(permissionDto);
    }

    @Override
    public PageBean<PermissionEntity> pagePermissionList(PermissionDto permission, PageParam pageParam) {
        List<PermissionEntity> permissionList = permissionDao.listPage(permission, pageParam);

        PageBean<PermissionEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(permissionDao.countListPage(permission))
                //设置查询结果
                .setContent(permissionList);
        if (pageParam.getPageSize() != null && pageParam.getCurrentPage() != null) {
            //设置当前页
            pageBean.setCurrentPage(pageParam.getCurrentPage())
                    //设置页大小
                    .setPageSize(pageParam.getPageSize())
                    //设置总页数 {(总记录数 + 页大小 - 1) / 页大小}
                    .setTotalPage((pageBean.getTotal() + pageBean.getPageSize() - 1) / pageBean.getPageSize())
                    //设置是否有后一页
                    .setHasNext(pageBean.getCurrentPage() < pageBean.getTotalPage())
                    //设置是否有前一页
                    .setHasPre(pageBean.getCurrentPage() > 1);
        }
        return pageBean;
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
}
