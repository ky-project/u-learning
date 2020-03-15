package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.teacher.dao.StudentExaminationTaskDao;
import com.ky.ulearning.teacher.service.StudentExaminationTaskService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生测试service - 实现
 *
 * @author luyuhao
 * @since 2020/03/16 00:48
 */
@Service
@CacheConfig(cacheNames = "studentExaminationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentExaminationTaskServiceImpl implements StudentExaminationTaskService {

    private StudentExaminationTaskDao studentExaminationTaskDao;
}
