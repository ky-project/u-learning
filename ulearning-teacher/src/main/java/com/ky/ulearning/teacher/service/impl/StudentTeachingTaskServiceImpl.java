package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.teacher.dto.StudentTeachingTaskDto;
import com.ky.ulearning.teacher.dao.StudentTeachingTaskDao;
import com.ky.ulearning.teacher.service.StudentTeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 学生选课service - 接口类
 *
 * 待添加 @CacheConfig(cacheNames = {"student", "course", "teacher"})
 *
 * @author luyuhao
 * @since 20/01/30 00:31
 */
@Service
@CacheConfig(cacheNames = "studentTeachingTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentTeachingTaskServiceImpl extends BaseService implements StudentTeachingTaskService {

    @Autowired
    private StudentTeachingTaskDao studentTeachingTaskDao;

    @Override
    public PageBean<StudentEntity> pageStudentList(StudentDto studentDto, Long teachingTaskId, PageParam pageParam) {
        List<StudentEntity> studentList = studentTeachingTaskDao.listPage(studentDto, teachingTaskId, pageParam);

        PageBean<StudentEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(studentTeachingTaskDao.countListPage(studentDto, teachingTaskId))
                //设置查询结果
                .setContent(studentList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    public Set<Long> getStuIdSetByTeachingTaskId(Set<Long> teachingTaskIdSet) {
        if(CollectionUtils.isEmpty(teachingTaskIdSet)){
            return Collections.emptySet();
        }
        return studentTeachingTaskDao.getStuIdSetByTeachingTaskId(teachingTaskIdSet);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void remove(StudentTeachingTaskDto studentTeachingTaskDto) {
        studentTeachingTaskDao.remove(studentTeachingTaskDto);
    }
}
