package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.system.auth.dao.TeacherDao;
import com.ky.ulearning.system.auth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 教师service 实现类
 *
 * @author luyuhao
 * @date 2019/12/5 12:57
 */
@Service
@CacheConfig(cacheNames = "teacher")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public TeacherEntity findByTeaNumber(String teaNumber) {
        return teacherDao.findByTeaNumber(teaNumber);
    }
}
