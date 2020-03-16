package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.teacher.dao.StudentExaminationTaskDao;
import com.ky.ulearning.teacher.service.StudentExaminationTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生测试service - 实现
 *
 * @author luyuhao
 * @since 2020/03/16 00:48
 */
@Service
@CacheConfig(cacheNames = "studentExaminationTask")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentExaminationTaskServiceImpl extends BaseService implements StudentExaminationTaskService {

    @Autowired
    private StudentExaminationTaskDao studentExaminationTaskDao;

    @Override
    public PageBean<StudentExaminationTaskDto> pageList(PageParam pageParam, StudentExaminationTaskDto studentExaminationTaskDto) {
        List<StudentExaminationTaskDto> resultList = studentExaminationTaskDao.listPage(studentExaminationTaskDto, pageParam);

        PageBean<StudentExaminationTaskDto> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(studentExaminationTaskDao.countListPage(studentExaminationTaskDto))
                //设置查询结果
                .setContent(resultList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    public StudentExaminationTaskDto getById(Long id) {
        return studentExaminationTaskDao.getById(id);
    }
}
