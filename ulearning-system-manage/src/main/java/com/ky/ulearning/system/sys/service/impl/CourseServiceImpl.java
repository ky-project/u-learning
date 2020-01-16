package com.ky.ulearning.system.sys.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.CourseDto;
import com.ky.ulearning.spi.system.entity.CourseEntity;
import com.ky.ulearning.spi.system.vo.CourseVo;
import com.ky.ulearning.system.sys.dao.CourseDao;
import com.ky.ulearning.system.sys.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(CourseDto courseDto) {
        //编号不可相同
        CourseEntity courseNumberExists = courseDao.getByCourseNumber(courseDto.getCourseNumber());
        if(courseNumberExists != null){
            throw new EntityExistException("课程编号");
        }

        courseDao.insert(courseDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public CourseEntity getById(Long id) {
        return courseDao.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(CourseDto courseDto) {
        //编号不可相同
        if(! StringUtil.isEmpty(courseDto.getCourseNumber())) {
            CourseEntity courseNumberExists = courseDao.getByCourseNumber(courseDto.getCourseNumber());
            if (courseNumberExists != null && ! courseDto.getId().equals(courseNumberExists.getId())) {
                throw new EntityExistException("课程编号");
            }
        }
        courseDao.update(courseDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updaterBy) {
        courseDao.updateValidById(id, 0, updaterBy);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<CourseVo> getAll() {
        return courseDao.getAllVo();
    }
}
