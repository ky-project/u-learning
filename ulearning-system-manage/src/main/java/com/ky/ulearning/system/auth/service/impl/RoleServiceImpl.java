package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.RoleDto;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.system.auth.dao.RoleDao;
import com.ky.ulearning.system.auth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色
 *
 * @author luyuhao
 * @since 19/12/08 18:31
 */
@Service
@CacheConfig(cacheNames = "role")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public PageBean<RoleEntity> pageRoleList(RoleDto roleDto, PageParam pageParam) {
        List<RoleEntity> roleEntityList = roleDao.listPage(roleDto, pageParam);

        PageBean<RoleEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(roleDao.countListPage(roleDto))
                //设置查询结果
                .setContent(roleEntityList);
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
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(RoleDto roleDto) {
        RoleEntity roleNameExists = roleDao.getByRoleName(roleDto.getRoleName());
        if(roleNameExists != null){
            throw new EntityExistException("角色名");
        }
        RoleEntity roleSourceExists = roleDao.getByRoleSource(roleDto.getRoleSource());
        if(roleSourceExists != null){
            throw new EntityExistException("角色资源名");
        }
        roleDao.insert(roleDto);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(allEntries = true)
    public void delete(Long id, String updateBy) {
        roleDao.updateValidById(id, 0, updateBy);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    @CacheEvict(allEntries = true)
    public void update(RoleDto roleDto) {
        if(!StringUtil.isEmpty(roleDto.getRoleName())) {
            RoleEntity roleNameExists = roleDao.getByRoleName(roleDto.getRoleName());
            if (roleNameExists != null && !roleDto.getId().equals(roleNameExists.getId())) {
                throw new EntityExistException("角色名");
            }
        }
        if(!StringUtil.isEmpty(roleDto.getRoleSource())) {
            RoleEntity roleSourceExists = roleDao.getByRoleSource(roleDto.getRoleSource());
            if (roleSourceExists != null && !roleDto.getId().equals(roleSourceExists.getId())) {
                throw new EntityExistException("角色资源名");
            }
        }

        roleDao.updateById(roleDto);
    }
}
