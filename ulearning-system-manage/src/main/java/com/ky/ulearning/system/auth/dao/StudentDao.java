package com.ky.ulearning.system.auth.dao;


import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 学生dao
 *
 * @author luyuhao
 * @since 2020/01/13 23:39
 */
@Mapper
@Repository
public interface StudentDao {

    /**
     * 插入学生信息
     *
     * @param studentDto 学生dto
     */
    void insert(StudentDto studentDto);

    /**
     * 根据id获取学生信息
     *
     * @param id 学生id
     * @return 学生对象
     */
    StudentEntity getById(Long id);

    /**
     * 更新学生信息
     *
     * @param studentDto 待更新的学生信息
     */
    void update(StudentDto studentDto);

    /**
     * 根据学号查询
     *
     * @param stuNumber 学生学号
     * @return 返回学生信息
     */
    StudentEntity getByStuNumber(String stuNumber);

    /**
     * 根据邮箱查询
     *
     * @param stuEmail 邮箱
     * @return 返回学生信息
     */
    StudentEntity getByStuEmail(String stuEmail);
}