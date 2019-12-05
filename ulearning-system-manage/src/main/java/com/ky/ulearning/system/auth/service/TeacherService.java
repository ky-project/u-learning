package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.system.entity.TeacherEntity;

/**
 * 教师service 接口类
 *
 * @author luyuhao
 * @date 2019/12/5 9:48
 */
public interface TeacherService {

    /**
     * 根据教师编号查询教师信息
     *
     * @param teaNumber 教师工号
     * @return 返回教师对象
     */
    TeacherEntity findByTeaNumber(String teaNumber);
}
