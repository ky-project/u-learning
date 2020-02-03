package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;
import com.ky.ulearning.spi.teacher.dto.QuestionDto;
import com.ky.ulearning.teacher.dao.CourseQuestionDao;
import com.ky.ulearning.teacher.service.CourseQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试题service - 实现类
 *
 * @author luyuhao
 * @since 20/02/03 19:53
 */
@Service
@CacheConfig(cacheNames = {"course", "question"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseQuestionServiceImpl extends BaseService implements CourseQuestionService {

    @Autowired
    private CourseQuestionDao courseQuestionDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(QuestionDto questionDto) {
        courseQuestionDao.insert(questionDto);
    }

    @Override
    public PageBean<CourseQuestionDto> pageList(PageParam pageParam, CourseQuestionDto courseQuestionDto) {
        List<CourseQuestionDto> studentList = courseQuestionDao.listPage(courseQuestionDto, pageParam);

        PageBean<CourseQuestionDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(courseQuestionDao.countListPage(courseQuestionDto))
                //设置查询结果
                .setContent(studentList);
        return setPageBeanProperties(pageBean, pageParam);
    }
}