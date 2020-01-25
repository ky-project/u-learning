package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;

/**
 * 学生service - 接口类
 *
 * @author luyuhao
 * @since 20/01/18 22:52
 */
public interface StudentService {

    /**
     * 插入学生信息
     *
     * @param studentDto 学生对象
     */
    void save(StudentDto studentDto);

    /**
     * 根据id查询学生信息
     *
     * @param id id
     * @return 当学生对象
     */
    StudentEntity getById(Long id);

    /**
     * 分页查询学生信息
     *
     * @param studentDto 筛选条件
     * @param pageParam  分页参数
     * @return 封装学生信息的分页对象
     */
    PageBean<StudentEntity> pageStudentList(StudentDto studentDto, PageParam pageParam);

    /**
     * 根据id删除学生信息
     *
     * @param id       学生id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);

    /**
     * 根据学号获取学生信息
     *
     * @param stuNumber 学号
     * @return 返回学生信息
     */
    StudentEntity getByStuNumber(String stuNumber);

    /**
     * 更新上次登录时间，不更新更新时间
     *
     * @param studentDto 待更新的学生对象
     */
    void updateLastLoginTime(StudentDto studentDto);
}
