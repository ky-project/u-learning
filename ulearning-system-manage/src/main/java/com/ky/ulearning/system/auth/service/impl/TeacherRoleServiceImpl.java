package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.system.auth.dao.TeacherRoleDao;
import com.ky.ulearning.system.auth.service.TeacherRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<RoleEntity> getRoleByTeaId(Long teaId) {
        return teacherRoleDao.getRoleByTeaId(teaId);
    }
}
