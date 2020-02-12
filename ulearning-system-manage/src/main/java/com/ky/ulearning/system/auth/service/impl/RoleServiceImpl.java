package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
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
import org.springframework.cache.annotation.Cacheable;
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
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public PageBean<RoleEntity> pageRoleList(RoleDto roleDto, PageParam pageParam) {
        List<RoleEntity> roleEntityList = roleDao.listPage(roleDto, pageParam);

        PageBean<RoleEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(roleDao.countListPage(roleDto))
                //设置查询结果
                .setContent(roleEntityList);
        return setPageBeanProperties(pageBean, pageParam);
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
