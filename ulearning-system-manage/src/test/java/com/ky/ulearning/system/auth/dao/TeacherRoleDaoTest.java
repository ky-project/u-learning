package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import com.ky.ulearning.spi.system.dto.RolePermissionDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 教师角色dao测试类
 *
 * @author luyuhao
 * @date 19/12/14 11:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TeacherRoleDaoTest {

    @Autowired
    private TeacherRoleDao teacherRoleDao;

    @BeforeClass
    public static void init(){
        EnvironmentAwareUtil.adjust();
    }

    @Test
    public void test01(){
        List<RoleEntity> rolePermissionByTeaId = teacherRoleDao.getRoleByTeaId(1L);
        for (RoleEntity roleEntity : rolePermissionByTeaId) {
            System.out.println(roleEntity);
        }
    }
}
