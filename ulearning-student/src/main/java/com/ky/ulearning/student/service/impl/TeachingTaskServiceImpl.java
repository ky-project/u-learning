package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.student.dao.TeachingTaskDao;
import com.ky.ulearning.student.service.TeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教学任务service - 实现
 *
 * @author luyuhao
 * @since 20/02/21 22:28
 */
@Service
@CacheConfig(cacheNames = "teachingTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class TeachingTaskServiceImpl extends BaseService implements TeachingTaskService {

    @Autowired
    private TeachingTaskDao teachingTaskDao;

    @Override
    public PageBean<TeachingTaskEntity> pageNotSelectedList(TeachingTaskDto teachingTaskDto, PageParam pageParam) {
        List<TeachingTaskEntity> teacherList = teachingTaskDao.listNotSelectedPage(teachingTaskDto, pageParam);

        return createPageBean(pageParam, teachingTaskDao.countNotSelectedListPage(teachingTaskDto), teacherList);
    }

    @Override
    public PageBean<TeachingTaskEntity> pageSelectedList(TeachingTaskDto teachingTaskDto, PageParam pageParam) {
        List<TeachingTaskEntity> teacherList = teachingTaskDao.listSelectedPage(teachingTaskDto, pageParam);

        return createPageBean(pageParam, teachingTaskDao.countSelectedListPage(teachingTaskDto), teacherList);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public Long getCourseIdById(Long id) {
        return teachingTaskDao.getCourseIdById(id);
    }

    /**
     * 根据id查询教学任务
     *
     * @param teachingTaskId 教学任务id
     * @return {@link TeachingTaskEntity}
     * @author luyuhao
     * @since 2021/9/10
     */
    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeachingTaskEntity getById(Long teachingTaskId) {
        return teachingTaskDao.getById(teachingTaskId);
    }
}
