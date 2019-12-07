package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.system.dto.TeacherRoleDto;
import com.ky.ulearning.spi.system.entity.TeacherRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师角色dao
 *
 * @author luyuhao
 * @date 19/12/08 03:19
 */
@Mapper
@Repository
public interface TeacherRoleDao {

    /**
     * 插入教师信息
     *
     * @param teacherRoleEntity 待插入的对象
     */
    void insert(TeacherRoleEntity teacherRoleEntity);

    /**
     * 根据id查询记录
     *
     * @param id 教师角色id
     * @return 教师角色实体类
     */
    TeacherRoleEntity getById(Long id);

    /**
     * 根据id更新记录
     *
     * @param teacherRoleEntity 待更新的对象
     */
    void updateById(TeacherRoleEntity teacherRoleEntity);

    /**
     * 根据教师id查询教师的所有角色
     * @param teaId 教师id
     * @return 返回教师角色集合
     */
    List<TeacherRoleDto> getByTeaId(Long teaId);
}
