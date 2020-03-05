package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.student.dao.ExperimentResultDao;
import com.ky.ulearning.student.service.ExperimentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实验结果service - 接口
 *
 * @author luyuhao
 * @since 20/03/06 01:51
 */
@Service
@CacheConfig(cacheNames = "experimentResult")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExperimentResultServiceImpl extends BaseService implements ExperimentResultService {

    @Autowired
    private ExperimentResultDao experimentResultDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public ExaminationTaskEntity getByExperimentIdAndStuId(Long experimentId, Long stuId) {
        return experimentResultDao.getByExperimentIdAndStuId(experimentId, stuId);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void add(ExperimentResultDto experimentResultDto) {
        experimentResultDao.insert(experimentResultDto);
    }

    @Override
    @Cacheable(keyGenerator =  "keyGenerator")
    public ExperimentResultEntity getById(Long id) {
        return experimentResultDao.getById(id);
    }
}
