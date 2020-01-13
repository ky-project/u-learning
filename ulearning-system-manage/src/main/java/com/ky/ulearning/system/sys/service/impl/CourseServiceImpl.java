package com.ky.ulearning.system.sys.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.CourseDto;
import com.ky.ulearning.spi.system.entity.CourseEntity;
import com.ky.ulearning.system.sys.dao.CourseDao;
import com.ky.ulearning.system.sys.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程service - 实现类
 *
 * @author luyuhao
 * @since 20/01/13 23:49
 */
@Service
@CacheConfig(cacheNames = "course")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseServiceImpl extends BaseService implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public PageBean<CourseEntity> pageCourseList(CourseDto courseDto, PageParam pageParam) {
        List<CourseEntity> teacherList = courseDao.listPage(courseDto, pageParam);

        PageBean<CourseEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(courseDao.countListPage(courseDto))
                //设置查询结果
                .setContent(teacherList);
        return setPageBeanProperties(pageBean, pageParam);
    }
}
