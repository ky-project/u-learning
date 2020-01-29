package com.ky.ulearning.teacher.dao;


import com.ky.ulearning.spi.teacher.dto.StudentTeachingTaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 学生选课dao
 *
 * @author luyuhao
 * @since 2020/01/30 00:25
 */
@Mapper
@Repository
public interface StudentTeachingTaskDao {
    /**
     * 删除关联信息
     *
     * @param id 关联id
     */
    void delete(Long id);

    /**
     * 插入关联信息
     *
     * @param studentTeachingTaskDto 待更新的关联对象
     */
    void insert(StudentTeachingTaskDto studentTeachingTaskDto);
}