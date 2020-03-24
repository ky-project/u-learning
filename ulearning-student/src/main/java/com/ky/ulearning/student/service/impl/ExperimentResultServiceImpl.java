package com.ky.ulearning.student.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.student.dao.ExperimentResultDao;
import com.ky.ulearning.student.service.ExperimentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public ExperimentResultEntity getByExperimentIdAndStuId(Long experimentId, Long stuId) {
        return experimentResultDao.getByExperimentIdAndStuId(experimentId, stuId);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void add(ExperimentResultDto experimentResultDto) {
        experimentResultDao.insert(experimentResultDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public ExperimentResultEntity getById(Long id) {
        return experimentResultDao.getById(id);
    }

    @Override
    public ExperimentResultDto getDetailByExperimentIdAndStuId(Long experimentId, Long stuId) {
        ExperimentResultDto experimentResultDto = experimentResultDao.getDetailByExperimentIdAndStuId(experimentId, stuId);
        if (Objects.isNull(experimentResultDto)) {
            return null;
        }
        //设置附件大小
        if (StringUtil.isNotEmpty(experimentResultDto.getExperimentUrl())) {
            FileInfo fileInfo = fastDfsClientWrapper.getFileInfo(experimentResultDto.getExperimentUrl());
            experimentResultDto.setExperimentAttachmentSize(fileInfo.getFileSize());
        }
        //设置是否已批改
        experimentResultDto.setIsCorrected(Objects.nonNull(experimentResultDto.getExperimentScore()) || Objects.nonNull(experimentResultDto.getExperimentAdvice()));
        //获取该实验的所有记录，根据分数降序排序
        List<ExperimentResultDto> experimentDtoList = experimentResultDao.listByScoreDesc(experimentId);
        int ranking = 1;
        for (ExperimentResultDto resultDto : experimentDtoList) {
            if (resultDto.getId().equals(experimentResultDto.getId())) {
                break;
            }
            ranking++;
        }
        experimentResultDto.setRanking(ranking);
        experimentResultDto.setSubmitNumber(experimentDtoList.size());
        return experimentResultDto;
    }

    @Override
    public PageBean<ExperimentResultDto> pageList(PageParam pageParam, ExperimentResultDto experimentResultDto) {
        List<ExperimentResultDto> resultList = experimentResultDao.listPage(experimentResultDto, pageParam);
        //获取该实验的所有记录，根据分数降序排序
        List<ExperimentResultDto> experimentDtoList = null;
        for (ExperimentResultDto resultDto : resultList) {
            if (StringUtil.isNotEmpty(resultDto.getExperimentUrl())) {
                resultDto.setExperimentAttachmentSize(fastDfsClientWrapper.getFileInfo(resultDto.getExperimentUrl()).getFileSize());
            }
            if (CollectionUtils.isEmpty(experimentDtoList)) {
                experimentDtoList = experimentResultDao.listByScoreDesc(resultDto.getExperimentId());
            }
            int ranking = 1;
            for (ExperimentResultDto resultDtoTemp : experimentDtoList) {
                if (resultDto.getId().equals(resultDtoTemp.getId())) {
                    break;
                }
                ranking++;
            }
            resultDto.setRanking(ranking);
            resultDto.setSubmitNumber(experimentDtoList.size());
        }

        PageBean<ExperimentResultDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(experimentResultDao.countListPage(experimentResultDto))
                //设置查询结果
                .setContent(resultList);
        return setPageBeanProperties(pageBean, pageParam);
    }
}
