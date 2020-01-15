package com.ky.ulearning.system.sys.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.system.sys.dao.TeachingTaskDao;
import com.ky.ulearning.system.sys.service.TeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 教学任务service - 实现类
 *
 * @author luyuhao
 * @since 20/01/16 00:29
 */
@Service
@CacheConfig(cacheNames = {"course", "teacher"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class TeachingTaskServiceImpl extends BaseService implements TeachingTaskService {

    @Autowired
    private TeachingTaskDao teachingTaskDao;

}
