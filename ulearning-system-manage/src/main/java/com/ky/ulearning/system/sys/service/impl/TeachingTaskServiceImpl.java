package com.ky.ulearning.system.sys.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.system.sys.dao.TeachingTaskDao;
import com.ky.ulearning.system.sys.service.TeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(TeachingTaskDto teachingTaskDto) {
        //判断是否存在相同记录
        TeachingTaskEntity teachingTaskEntity = teachingTaskDao.getByTeaIdAndCourseIdAndTermAndAlias(teachingTaskDto);
        if(teachingTaskEntity != null){
            throw new EntityExistException("教学任务");
        }
        teachingTaskDao.insert(teachingTaskDto);
    }
}
