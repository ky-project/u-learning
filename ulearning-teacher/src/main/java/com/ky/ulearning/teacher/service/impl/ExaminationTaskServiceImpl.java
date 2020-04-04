package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.dto.ExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.teacher.dao.ExaminationTaskDao;
import com.ky.ulearning.teacher.service.ExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试任务service - 实现类
 *
 * @author luyuhao
 * @since 20/02/13 00:57
 */
@Service
@CacheConfig(cacheNames = "examinationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExaminationTaskServiceImpl extends BaseService implements ExaminationTaskService {

    @Autowired
    private ExaminationTaskDao examinationTaskDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(ExaminationTaskDto examinationTaskDto) {
        examinationTaskDao.insert(examinationTaskDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public PageBean<ExaminationTaskEntity> pageExaminationTaskList(ExaminationTaskDto examinationTaskDto, PageParam pageParam) {
        List<ExaminationTaskEntity> studentList = examinationTaskDao.listPage(examinationTaskDto, pageParam);

        PageBean<ExaminationTaskEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(examinationTaskDao.countListPage(examinationTaskDto))
                //设置查询结果
                .setContent(studentList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public ExaminationTaskEntity getById(Long examinationId) {
        return examinationTaskDao.getById(examinationId);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(ExaminationTaskDto examinationTaskDto) {
        examinationTaskDao.update(examinationTaskDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        examinationTaskDao.updateValidById(id, updateBy, 0);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<KeyLabelVo> getArrByTeachingTaskId(Long teachingTaskId) {
        return examinationTaskDao.getArrByTeachingTaskId(teachingTaskId);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public String getExaminationParameters(Long id) {
        return examinationTaskDao.getExaminationParameters(id);
    }

    @Override
    public Integer getExaminationNumber(Long teaId) {
        return examinationTaskDao.getExaminationNumber(teaId);
    }
}
