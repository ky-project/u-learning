package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.teacher.dao.TeachingTaskDao;
import com.ky.ulearning.teacher.service.TeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author luyuhao
 * @since 20/01/26 16:16
 */
@Service
@CacheConfig(cacheNames = {"course", "teacher"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class TeachingTaskServiceImpl extends BaseService implements TeachingTaskService {

    @Autowired
    private TeachingTaskDao teachingTaskDao;

    @Override
    public PageBean<CourseTeachingTaskDto> pageList(PageParam pageParam, CourseTeachingTaskDto courseTeachingTaskDto) {
        List<CourseTeachingTaskDto> teacherList = teachingTaskDao.listPage(courseTeachingTaskDto, pageParam);

        PageBean<CourseTeachingTaskDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(teachingTaskDao.countListPage(courseTeachingTaskDto))
                //设置查询结果
                .setContent(teacherList);
        return setPageBeanProperties(pageBean, pageParam);
    }

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
    public Set<Long> getIdByTeaId(Long teaId) {
        return teachingTaskDao.getIdByTeaId(teaId);
    }
}
