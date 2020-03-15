package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.teacher.dao.ExaminationResultDao;
import com.ky.ulearning.teacher.service.ExaminationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luyuhao
 * @since 2020/03/16 00:54
 */
@Service
@CacheConfig(cacheNames = "examinationResult")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExaminationResultServiceImpl implements ExaminationResultService {

    @Autowired
    private ExaminationResultDao examinationResultDao;
}
