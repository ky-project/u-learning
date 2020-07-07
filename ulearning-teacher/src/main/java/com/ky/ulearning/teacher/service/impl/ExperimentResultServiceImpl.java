package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.teacher.dao.ExperimentResultDao;
import com.ky.ulearning.teacher.service.ExperimentResultService;
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
 * 实验结果service - 实现
 *
 * @author luyuhao
 * @since 2020/3/9 21:57
 */
@Service
@CacheConfig(cacheNames = "experimentResult")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExperimentResultServiceImpl extends BaseService implements ExperimentResultService {

    @Autowired
    private ExperimentResultDao experimentResultDao;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Override
    public PageBean<ExperimentResultDto> pageList(PageParam pageParam, ExperimentResultDto experimentResultDto) {
        List<ExperimentResultDto> resultList = Optional.ofNullable(experimentResultDao.listPage(experimentResultDto, pageParam))
                .orElse(Collections.emptyList());
        for (ExperimentResultDto resultDto : resultList) {
            if (StringUtil.isNotEmpty(resultDto.getExperimentUrl())) {
                resultDto.setExperimentAttachmentSize(fastDfsClientWrapper.getFileInfo(resultDto.getExperimentUrl()).getFileSize());
            }
            resultDto.setIsCorrected(StringUtil.isNotEmpty(resultDto.getExperimentAdvice()) || StringUtil.isNotEmpty(resultDto.getExperimentScore()));
        }
        return createPageBean(pageParam, experimentResultDao.countListPage(experimentResultDto), resultList);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public ExperimentResultDto getById(Long id) {
        ExperimentResultDto experimentResultDto = experimentResultDao.getById(id);
        if (StringUtil.isNotEmpty(experimentResultDto)) {
            if (StringUtil.isNotEmpty(experimentResultDto.getExperimentUrl())) {
                experimentResultDto.setExperimentAttachmentSize(fastDfsClientWrapper.getFileInfo(experimentResultDto.getExperimentUrl()).getFileSize());
            }
            experimentResultDto.setIsCorrected(StringUtil.isNotEmpty(experimentResultDto.getExperimentAdvice()) || StringUtil.isNotEmpty(experimentResultDto.getExperimentScore()));
        }
        return experimentResultDto;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(ExperimentResultEntity experimentResultEntity) {
        experimentResultDao.update(experimentResultEntity);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void sharedExperimentResult(Long id, String username, Boolean experimentShared) {
        experimentResultDao.updateSharedById(id, username, experimentShared);
    }

    /**
     * 查询所有实验结果
     *
     * @param experimentId 实验id
     * @return 实验结果集合
     * @author luyuhao
     * @date 20/07/08 02:33
     */
    @Override
    public List<ExperimentResultDto> listByExperimentId(Long experimentId) {
        return experimentResultDao.listByExperimentId(experimentId);
    }
}
