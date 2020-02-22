package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.student.dao.TeachingTaskNoticeDao;
import com.ky.ulearning.student.service.TeachingTaskNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author luyuhao
 * @since 20/02/22 17:08
 */
@Service
@CacheConfig(cacheNames = "teachingTaskNotice")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class TeachingTaskNoticeServiceImpl extends BaseService implements TeachingTaskNoticeService {

    @Autowired
    private TeachingTaskNoticeDao teachingTaskNoticeDao;

    @Override
    public PageBean<TeachingTaskNoticeEntity> pageList(PageParam pageParam, Long teachingTaskId) {
        List<TeachingTaskNoticeEntity> teacherList = teachingTaskNoticeDao.listPage(teachingTaskId, pageParam);

        PageBean<TeachingTaskNoticeEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(teachingTaskNoticeDao.countListPage(teachingTaskId))
                //设置查询结果
                .setContent(teacherList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public Set<Long> getIdSetByTeachingTaskIdSet(Set<Long> teachingTaskIdSet) {
        if(CollectionUtils.isEmpty(teachingTaskIdSet)){
            return Collections.emptySet();
        }
        return teachingTaskNoticeDao.getIdSetByTeachingTaskIdSet(teachingTaskIdSet);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeachingTaskNoticeEntity getById(Long id) {
        return teachingTaskNoticeDao.getById(id);
    }
}
