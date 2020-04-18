package com.ky.ulearning.student.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.StudentTeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.student.dao.TeachingTaskExperimentDao;
import com.ky.ulearning.student.service.TeachingTaskExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Override
    public PageBean<StudentTeachingTaskExperimentDto> pageList(ExperimentDto experimentDto, PageParam pageParam, Long stuId) {
        List<StudentTeachingTaskExperimentDto> studentTeachingTaskExperimentDtoList = Optional.ofNullable(teachingTaskExperimentDao.listPage(experimentDto, pageParam, stuId))
                .orElse(Collections.emptyList());
        //判断是否已提交该实验结果和计算附件大小
        for (StudentTeachingTaskExperimentDto studentTeachingTaskExperimentDto : studentTeachingTaskExperimentDtoList) {
            //计算附件大小
            if (StringUtil.isNotEmpty(studentTeachingTaskExperimentDto.getExperimentAttachment())) {
                FileInfo fileInfo = fastDfsClientWrapper.getFileInfo(studentTeachingTaskExperimentDto.getExperimentAttachment());
                if (fileInfo == null) {
                    continue;
                }
                studentTeachingTaskExperimentDto.setExperimentAttachmentSize(fileInfo.getFileSize());
            }
        }

        //设置总记录数
        Integer total = teachingTaskExperimentDao.countListPage(experimentDto);
        return createPageBean(pageParam, total, studentTeachingTaskExperimentDtoList);
    }

    @Override
    public TeachingTaskExperimentDto getById(Long id) {
        return teachingTaskExperimentDao.getById(id);
    }
}
