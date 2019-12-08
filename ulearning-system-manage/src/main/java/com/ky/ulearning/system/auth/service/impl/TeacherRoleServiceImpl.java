package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.spi.system.dto.RolePermissionDto;
import com.ky.ulearning.system.auth.dao.TeacherRoleDao;
import com.ky.ulearning.system.auth.service.TeacherRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/08 20:45
 */
@Service
public class TeacherRoleServiceImpl implements TeacherRoleService {

    @Autowired
    private TeacherRoleDao teacherRoleDao;

    @Override
    public List<RolePermissionDto> getRolePermissionByTeaId(Long teaId) {
        return teacherRoleDao.getRolePermissionByTeaId(teaId);
    }
}
