package com.ky.ulearning.teacher.service.impl;

import com.google.common.base.Joiner;
import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.teacher.dao.TeachingTaskNoticeDao;
import com.ky.ulearning.teacher.service.TeachingTaskNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Override
    public PageBean<TeachingTaskNoticeDto> pageList(PageParam pageParam, TeachingTaskNoticeDto teachingTaskNoticeDto) {
        List<TeachingTaskNoticeDto> teacherList = Optional.ofNullable(teachingTaskNoticeDao.listPage(teachingTaskNoticeDto, pageParam))
                .orElse(Collections.emptyList());

        for (TeachingTaskNoticeDto taskNoticeDto : teacherList) {
            taskNoticeDto.setNoticeAttachmentSize(calAttachmentSize(taskNoticeDto.getNoticeAttachment()));
        }

        PageBean<TeachingTaskNoticeDto> pageBean = new PageBean<>();
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

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeachingTaskNoticeEntity getById(Long id) {
        return teachingTaskNoticeDao.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(TeachingTaskNoticeDto teachingTaskNoticeDto) {
        teachingTaskNoticeDao.update(teachingTaskNoticeDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        teachingTaskNoticeDao.updateValid(id, updateBy, 0);
    }

    /**
     * 根据url查询文件大小，并返回大小数组，逗号分隔
     *
     * @param noticeAttachment 附件
     * @return 大小字符串
     */
    public String calAttachmentSize(String noticeAttachment) {
        if (StringUtil.isNotEmpty(noticeAttachment)) {
            String[] fileUrlArray = StringUtils.split(noticeAttachment, ",");
            List<Long> fileSizeList = fastDfsClientWrapper.getFileSizeList(fileUrlArray);
            return Joiner.on(",").join(fileSizeList);
        }
        return "";
    }
}
