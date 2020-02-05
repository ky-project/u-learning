package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.teacher.dao.TeachingTaskExperimentDao;
import com.ky.ulearning.teacher.service.TeachingTaskExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/04 21:00
 */
@Service
@CacheConfig(cacheNames = {"course", "teacher", "experiment"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class TeachingTaskExperimentServiceImpl extends BaseService implements TeachingTaskExperimentService {

    @Autowired
    private TeachingTaskExperimentDao teachingTaskExperimentDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(ExperimentDto experimentDto) {
        teachingTaskExperimentDao.insert(experimentDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeachingTaskExperimentDto getById(Long id) {
        return teachingTaskExperimentDao.getById(id);
    }

    @Override
    public PageBean<TeachingTaskExperimentDto> pageList(PageParam pageParam, ExperimentDto experimentDto) {
        List<TeachingTaskExperimentDto> teacherList = teachingTaskExperimentDao.listPage(experimentDto, pageParam);

        PageBean<TeachingTaskExperimentDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(teachingTaskExperimentDao.countListPage(experimentDto))
                //设置查询结果
                .setContent(teacherList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(ExperimentDto experimentDto) {
        teachingTaskExperimentDao.update(experimentDto);
    }
}
