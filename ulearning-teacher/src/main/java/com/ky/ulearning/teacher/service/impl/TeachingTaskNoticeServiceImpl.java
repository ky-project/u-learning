package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.teacher.dao.TeachingTaskNoticeDao;
import com.ky.ulearning.teacher.service.TeachingTaskNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 通告service - 实现类
 *
 * @author luyuhao
 * @since 20/01/30 23:37
 */
@Service
@CacheConfig(cacheNames = "teachingTaskNotice")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class TeachingTaskNoticeServiceImpl extends BaseService implements TeachingTaskNoticeService {

    @Autowired
    private TeachingTaskNoticeDao teachingTaskNoticeDao;
}
