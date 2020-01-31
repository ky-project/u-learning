package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.teacher.dao.TeachingTaskNoticeDao;
import com.ky.ulearning.teacher.service.TeachingTaskNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 通告service - 实现类
 *
 * @author luyuhao
 * @since 20/01/30 23:37
 */
@Service
@CacheConfig(cacheNames = "teachingTaskNotice")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class TeachingTaskNoticeServiceImpl extends BaseService implements TeachingTaskNoticeService {

    @Autowired
    private TeachingTaskNoticeDao teachingTaskNoticeDao;

    @Override
    public PageBean<TeachingTaskNoticeEntity> pageList(PageParam pageParam, TeachingTaskNoticeDto teachingTaskNoticeDto) {
        List<TeachingTaskNoticeEntity> teacherList = teachingTaskNoticeDao.listPage(teachingTaskNoticeDto, pageParam);

        PageBean<TeachingTaskNoticeEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(teachingTaskNoticeDao.countListPage(teachingTaskNoticeDto))
                //设置查询结果
                .setContent(teacherList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(TeachingTaskNoticeDto teachingTaskNoticeDto) {
        teachingTaskNoticeDao.insert(teachingTaskNoticeDto);
    }
}
