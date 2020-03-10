package com.ky.ulearning.student.service.impl;

import com.google.common.base.Joiner;
import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.student.dao.TeachingTaskNoticeDao;
import com.ky.ulearning.student.service.TeachingTaskNoticeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
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
    @Cacheable(keyGenerator = "keyGenerator")
    public Set<Long> getIdSetByTeachingTaskIdSet(Set<Long> teachingTaskIdSet) {
        if (CollectionUtils.isEmpty(teachingTaskIdSet)) {
            return Collections.emptySet();
        }
        return teachingTaskNoticeDao.getIdSetByTeachingTaskIdSet(teachingTaskIdSet);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeachingTaskNoticeEntity getById(Long id) {
        return teachingTaskNoticeDao.getById(id);
    }

    /**
     * 根据url查询文件大小，并返回大小数组，逗号分隔
     *
     * @param noticeAttachment 附件
     * @return 大小字符串
     */
    private String calAttachmentSize(String noticeAttachment) {
        if (StringUtil.isNotEmpty(noticeAttachment)) {
            String[] fileUrlArray = StringUtils.split(noticeAttachment, ",");
            List<Long> fileSizeList = fastDfsClientWrapper.getFileSizeList(fileUrlArray);
            return Joiner.on(",").join(fileSizeList);
        }
        return "";
    }
}
