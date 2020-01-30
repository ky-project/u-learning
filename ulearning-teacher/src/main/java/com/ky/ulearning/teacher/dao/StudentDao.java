package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.system.entity.StudentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author luyuhao
 * @since 20/01/30 20:51
 */
@Mapper
@Repository
public interface StudentDao {

    /**
     * 根据id获取学生信息
     *
     * @param id 学生id
     * @return 学生对象
     */
    StudentEntity getById(Long id);
}
