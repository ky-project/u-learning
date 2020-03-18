package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.dto.ExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.student.dao.ExaminationTaskDao;
import com.ky.ulearning.student.service.ExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试任务service - 实现类
 *
 * @author luyuhao
 * @since 20/03/07 23:56
 */
@Service
@CacheConfig(cacheNames = "examinationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExaminationTaskServiceImpl extends BaseService implements ExaminationTaskService {

    @Autowired
    private ExaminationTaskDao examinationTaskDao;

    @Override
    public PageBean<ExaminationTaskDto> pageExaminationTaskList(ExaminationTaskDto examinationTaskDto, PageParam pageParam) {
        List<ExaminationTaskDto> resultList = examinationTaskDao.listPage(examinationTaskDto, pageParam);

        PageBean<ExaminationTaskDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(examinationTaskDao.countListPage(examinationTaskDto))
                //设置查询结果
                .setContent(resultList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public ExaminationTaskEntity getById(Long id) {
        return examinationTaskDao.getById(id);
    }

    @Override
    public List<KeyLabelVo> getExaminationTaskArr(Long teachingTaskId, Long stuId) {
        return examinationTaskDao.getExaminationTaskArr(teachingTaskId, stuId);
    }
}
