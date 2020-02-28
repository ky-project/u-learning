package com.ky.ulearning.teacher.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity;
import com.ky.ulearning.teacher.dao.TeachingTaskExperimentDao;
import com.ky.ulearning.teacher.service.TeachingTaskExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author luyuhao
 * @since 20/02/04 21:00
 */
@Service
@CacheConfig(cacheNames = "teachingTaskExperiment")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class TeachingTaskExperimentServiceImpl extends BaseService implements TeachingTaskExperimentService {

    @Autowired
    private TeachingTaskExperimentDao teachingTaskExperimentDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(ExperimentDto experimentDto) {
        //设置order递增
        //查询所有试验，根据order递减排序
        List<TeachingTaskExperimentEntity> teachingTaskExperimentEntityList = teachingTaskExperimentDao.listByTeachingTaskId(experimentDto.getTeachingTaskId());
        if (CollectionUtil.isEmpty(teachingTaskExperimentEntityList)) {
            experimentDto.setExperimentOrder(1);
        } else {
            experimentDto.setExperimentOrder(teachingTaskExperimentEntityList.get(0).getExperimentOrder() + 1);
        }
        teachingTaskExperimentDao.insert(experimentDto);
    }

    @Override
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
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskExperimentDao.getById(experimentDto.getId());
        //修改实验序号时，需要对其他记录的实验序号依次处理
        if (StringUtil.isNotEmpty(experimentDto.getExperimentOrder())
                && !teachingTaskExperimentDto.getExperimentOrder().equals(experimentDto.getExperimentOrder())) {
            List<ExperimentDto> experimentDtoList = teachingTaskExperimentDao.listDtoByTeachingTaskId(teachingTaskExperimentDto.getTeachingTaskId());
            //原order>新order，<=原order&&>=新order的记录order++；原order<新order，>=原order&&<=新order的记录order--
            if (teachingTaskExperimentDto.getExperimentOrder() > experimentDto.getExperimentOrder()) {
                for (ExperimentDto experimentDtoTmp : experimentDtoList) {
                    if (experimentDtoTmp.getExperimentOrder() >= experimentDto.getExperimentOrder()
                            && experimentDtoTmp.getExperimentOrder() <= teachingTaskExperimentDto.getExperimentOrder()
                            && !experimentDtoTmp.getId().equals(experimentDto.getId())) {
                        experimentDtoTmp.setExperimentOrder(experimentDtoTmp.getExperimentOrder() + 1);
                        teachingTaskExperimentDao.update(experimentDtoTmp);
                    }
                }
            } else{
                for (ExperimentDto experimentDtoTmp : experimentDtoList) {
                    if (experimentDtoTmp.getExperimentOrder() <= experimentDto.getExperimentOrder()
                            && experimentDtoTmp.getExperimentOrder() >= teachingTaskExperimentDto.getExperimentOrder()
                            && !experimentDtoTmp.getId().equals(experimentDto.getId())) {
                        experimentDtoTmp.setExperimentOrder(experimentDtoTmp.getExperimentOrder() - 1);
                        teachingTaskExperimentDao.update(experimentDtoTmp);
                    }
                }
            }
        }
        teachingTaskExperimentDao.update(experimentDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<TeachingTaskExperimentEntity> listByTeachingTaskId(Long teachingTaskId) {
        return Optional.ofNullable(teachingTaskExperimentDao.listByTeachingTaskId(teachingTaskId))
                .orElse(Collections.emptyList());
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        teachingTaskExperimentDao.delete(id, updateBy);
    }
}
