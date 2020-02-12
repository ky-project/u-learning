package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.teacher.dao.ExaminationTaskDao;
import com.ky.ulearning.teacher.service.ExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试任务service - 实现类
 *
 * @author luyuhao
 * @since 20/02/13 00:57
 */
@Service
@CacheConfig(cacheNames = "examinationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExaminationTaskServiceImpl extends BaseService implements ExaminationTaskService {

    @Autowired
    private ExaminationTaskDao examinationTaskDao;

}
