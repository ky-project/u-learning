package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.system.dto.RolePermissionDto;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师角色dao
 *
 * @author Darren
 * @date 2019/12/08 18:20
 */
@Mapper
@Repository
public interface TeacherRoleDao {
    /**
     * 根据id删除教师角色
     *
     * @param id 教师角色id
     */
    void deleteById(Long id);

    /**
     * 插入教师角色记录
     *
     * @param teacherRole 教师角色对象
     */
    void insert(TeacherRoleEntity teacherRole);

    /**
     * 根据id查询教师角色对象
     *
     * @param id 教师角色id
     * @return 教师角色对象
     */
    TeacherRoleEntity getById(Long id);

    /**
     * 根据id更新教师角色记录
     *
     * @param teacherRole 待更新的教师角色对象
     */
    void updateById(TeacherRoleEntity teacherRole);

    /**
     * 根据教师id获取角色集合
     *
     * @param teaId 教师id
     * @return 返回角色集合
     */
    List<RoleEntity> getRoleByTeaId(Long teaId);

    /**
     * 根据教师id删除关联记录
     *
     * @param teaId 教师id
     */
    void deleteByTeaId(Long teaId);

    /**
     * 批量添加教师角色关联记录
     *
     * @param teacherRoleList 教师角色对象集合
     */
    void batchInsert(@Param("teacherRoleList") List<TeacherRoleEntity> teacherRoleList);
}