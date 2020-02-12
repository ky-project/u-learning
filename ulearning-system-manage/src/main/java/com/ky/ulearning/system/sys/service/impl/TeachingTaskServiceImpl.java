package com.ky.ulearning.system.sys.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.system.sys.dao.TeachingTaskDao;
import com.ky.ulearning.system.sys.service.TeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教学任务service - 实现类
 *
 * @author luyuhao
 * @since 20/01/16 00:29
 */
@Service
@CacheConfig(cacheNames = "teachingTask")
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
        if (teachingTaskEntity != null) {
            throw new EntityExistException("教学任务");
        }
        teachingTaskDao.insert(teachingTaskDto);
    }

    @Override
    public PageBean<TeachingTaskEntity> pageTeachingTaskList(TeachingTaskDto teachingTaskDto, PageParam pageParam) {
        List<TeachingTaskEntity> teacherList = teachingTaskDao.listPage(teachingTaskDto, pageParam);

        PageBean<TeachingTaskEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(teachingTaskDao.countListPage(teachingTaskDto))
                //设置查询结果
                .setContent(teacherList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(TeachingTaskDto teachingTaskDto) {
        //判断是否存在相同记录
        TeachingTaskEntity teachingTaskEntity = teachingTaskDao.getByTeaIdAndCourseIdAndTermAndAlias(teachingTaskDto);
        if (teachingTaskEntity != null && !teachingTaskEntity.getId().equals(teachingTaskDto.getId())) {
            throw new EntityExistException("教学任务");
        }
        teachingTaskDao.update(teachingTaskDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeachingTaskEntity getById(Long id) {
        return teachingTaskDao.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        teachingTaskDao.updateValidById(id, 0, updateBy);
    }
}
