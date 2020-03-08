package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.student.dao.StudentExaminationTaskDao;
import com.ky.ulearning.student.service.StudentExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
