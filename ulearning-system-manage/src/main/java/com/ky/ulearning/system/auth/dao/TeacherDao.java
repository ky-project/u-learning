package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.system.dto.UpdateTeacherDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 教师dao 接口类
 *
 * @author luyuhao
 * @date 2019/12/5 12:57
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

    /**
     * 根据教师编号查询教师信息
     *
     * @param teaNumber 教师编号
     * @return 返回教师对象
     */
    TeacherEntity findByTeaNumber(String teaNumber);

    /**
     * 根据教师邮箱查询教师信息
     *
     * @param teaEmail 教师邮箱
     * @return 返回教师对象
     */
    TeacherEntity findByEmail(String teaEmail);

    /**
     * 更新教师信息
     *
     * @param teacher 待更新的教师
     */
    void updateById(UpdateTeacherDto teacher);
}
