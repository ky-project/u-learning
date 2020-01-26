package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.system.entity.TeacherEntity;

/**
 * @author luyuhao
 * @since 20/01/26 21:45
 */
public interface TeacherService {

    /**
     * 根据教师编号查询教师信息
     *
     * @param teaNumber 教师工号
     * @return 返回教师对象
     */
    TeacherEntity getByTeaNumber(String teaNumber);
}
