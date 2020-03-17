package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;
import com.ky.ulearning.spi.student.vo.StudentExaminationTaskBaseInfoVo;
import com.ky.ulearning.student.dao.StudentExaminationTaskDao;
import com.ky.ulearning.student.service.StudentExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 学生测试service - 实现
 *
 * @author luyuhao
 * @since 2020/03/08 00:53
 */
@Service
@CacheConfig(cacheNames = "studentExaminationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentExaminationTaskServiceImpl extends BaseService implements StudentExaminationTaskService {

    @Autowired
    private StudentExaminationTaskDao studentExaminationTaskDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void add(StudentExaminationTaskDto studentExaminationTaskDto) {
        studentExaminationTaskDao.insert(studentExaminationTaskDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentExaminationTaskEntity getByExaminationTaskIdAndStuId(Long examinationTaskId, Long stuId) {
        return studentExaminationTaskDao.getByExaminationTaskIdAndStuId(examinationTaskId, stuId);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(StudentExaminationTaskDto studentExaminationTaskDto) {
        studentExaminationTaskDao.update(studentExaminationTaskDto);
    }

    @Override
    public List<StudentExaminationTaskBaseInfoVo> getBaseInfoByExaminationTaskId(Long examinationTaskId) {
        return Optional.ofNullable(studentExaminationTaskDao.getBaseInfoByExaminationTaskId(examinationTaskId))
                .orElse(Collections.emptyList());
    }
}
