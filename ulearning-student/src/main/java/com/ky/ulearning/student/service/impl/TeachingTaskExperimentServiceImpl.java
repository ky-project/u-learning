package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.student.dao.TeachingTaskExperimentDao;
import com.ky.ulearning.student.service.TeachingTaskExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实验service - 实现
 *
 * @author luyuhao
 * @since 20/03/05 00:54
 */
@Service
@CacheConfig(cacheNames = "teachingTaskExperiment")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class TeachingTaskExperimentServiceImpl extends BaseService implements TeachingTaskExperimentService {

    @Autowired
    private TeachingTaskExperimentDao teachingTaskExperimentDao;

    @Override
    public PageBean<TeachingTaskExperimentDto> pageList(ExperimentDto experimentDto, PageParam pageParam) {
        List<TeachingTaskExperimentDto> teacherList = teachingTaskExperimentDao.listPage(experimentDto, pageParam);

        PageBean<TeachingTaskExperimentDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(teachingTaskExperimentDao.countListPage(experimentDto))
                //设置查询结果
                .setContent(teacherList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    public TeachingTaskExperimentDto getById(Long id) {
        return teachingTaskExperimentDao.getById(id);
    }
}
