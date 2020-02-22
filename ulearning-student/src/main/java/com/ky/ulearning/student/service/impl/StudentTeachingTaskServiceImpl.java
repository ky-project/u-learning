package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;
import com.ky.ulearning.student.dao.StudentTeachingTaskDao;
import com.ky.ulearning.student.service.StudentTeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author luyuhao
 * @since 20/02/22 14:41
 */
@Service
@CacheConfig(cacheNames = "studentTeachingTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentTeachingTaskServiceImpl extends BaseService implements StudentTeachingTaskService {

    @Autowired
    private StudentTeachingTaskDao studentTeachingTaskDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentTeachingTaskEntity getByTeachingIdAndStuId(Long teachingTaskId, Long stuId) {
        return studentTeachingTaskDao.getByTeachingIdAndStuId(teachingTaskId, stuId);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(StudentTeachingTaskEntity studentTeachingTaskEntity) {
        studentTeachingTaskDao.insert(studentTeachingTaskEntity);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void deleteByTeachingTaskIdAndStuId(Long teachingTaskId, Long stuId, String updateBy) {
        studentTeachingTaskDao.deleteByTeachingTaskIdAndStuId(teachingTaskId, stuId, updateBy);
    }

    @Override
    public List<KeyLabelVo> getTeachingTaskArray(Long stuId) {
        return studentTeachingTaskDao.getTeachingTaskArray(stuId);
    }

    @Override
    public Set<Long> getTeachingTaskIdSetByStuId(Long stuId) {
        return studentTeachingTaskDao.getTeachingTaskIdSetByStuId(stuId);
    }
}
