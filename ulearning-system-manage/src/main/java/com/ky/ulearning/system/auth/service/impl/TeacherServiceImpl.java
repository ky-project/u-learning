package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.spi.system.dto.UpdateTeacherDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.system.auth.dao.TeacherDao;
import com.ky.ulearning.system.auth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 教师service 实现类
 *
 * @author luyuhao
 * @date 2019/12/5 12:57
 */
@Service
@CacheConfig(cacheNames = "teacher")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeacherEntity getByTeaNumber(String teaNumber) {
        return teacherDao.getByTeaNumber(teaNumber);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(UpdateTeacherDto newTeacher) {
        //判断teaNumber是否存在
        if (!StringUtils.isEmpty(newTeacher.getTeaNumber())
                && teacherDao.findByTeaNumber(newTeacher.getTeaNumber()) != null) {
            throw new EntityExistException("教师编号");
        }
        //判断邮箱是否存在
        if (!StringUtils.isEmpty(newTeacher.getTeaEmail())
                && teacherDao.findByEmail(newTeacher.getTeaEmail()) != null) {
            throw new EntityExistException("教师邮箱");
        }
        //更新记录
        teacherDao.updateById(newTeacher);
    }
}
