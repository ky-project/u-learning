package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author luyuhao
 * @since 20/01/26 21:48
 */
@Mapper
@Repository
public interface TeacherDao {
    /**
     * 根据教师编号查询教师信息
     *
     * @param teaNumber 教师工号
     * @return 返回教师对象
     */
    TeacherEntity getByTeaNumber(@Param("teaNumber") String teaNumber);
}
