package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.system.auth.dao.TeacherDao;
import com.ky.ulearning.system.auth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 教师service 实现类
 *
 * @author luyuhao
 * @date 2019/12/5 12:57
 */
@Service
@CacheConfig(cacheNames = "teacher")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeacherEntity getByTeaNumber(String teaNumber) {
        return teacherDao.getByTeaNumber(teaNumber);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(TeacherDto newTeacher) {
        //判断teaNumber是否存在
        TeacherEntity teaNumberExists = teacherDao.findByTeaNumber(newTeacher.getTeaNumber());
        if (!StringUtils.isEmpty(newTeacher.getTeaNumber())
                && teaNumberExists != null
                && !teaNumberExists.getId().equals(newTeacher.getId())) {
            throw new EntityExistException("教师编号");
        }
        //判断邮箱是否存在
        TeacherEntity emailExists = teacherDao.findByEmail(newTeacher.getTeaEmail());
        if (!StringUtils.isEmpty(newTeacher.getTeaEmail())
                && emailExists != null
                && !emailExists.getId().equals(newTeacher.getId())) {
            throw new EntityExistException("教师邮箱");
        }
        //更新记录
        teacherDao.updateById(newTeacher);
    }

    @Override
    public PageBean<TeacherEntity> pageTeacherList(TeacherDto teacherDto, PageParam pageParam) {
        List<TeacherEntity> teacherList = teacherDao.listPage(teacherDto, pageParam);

        PageBean<TeacherEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(teacherDao.countListPage(teacherDto))
                //设置查询结果
                .setContent(teacherList);
        if (pageParam.getPageSize() != null && pageParam.getCurrentPage() != null) {
            //设置当前页
            pageBean.setCurrentPage(pageParam.getCurrentPage())
                    //设置页大小
                    .setPageSize(pageParam.getPageSize())
                    //设置总页数 {(总记录数 + 页大小 - 1) / 页大小}
                    .setTotalPage((pageBean.getTotal() + pageBean.getPageSize() - 1) / pageBean.getPageSize())
                    //设置是否有后一页
                    .setHasNext(pageBean.getCurrentPage() < pageBean.getTotalPage())
                    //设置是否有前一页
                    .setHasPre(pageBean.getCurrentPage() > 1);
        }
        return pageBean;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id) {
        teacherDao.updateValidByTeaId(id, 0);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(TeacherDto teacher) {
        //判断teaNumber是否存在
        if (!StringUtils.isEmpty(teacher.getTeaNumber())
                && teacherDao.findByTeaNumber(teacher.getTeaNumber()) != null) {
            throw new EntityExistException("教师编号");
        }
        //判断邮箱是否存在
        if (!StringUtils.isEmpty(teacher.getTeaEmail())
                && teacherDao.findByEmail(teacher.getTeaEmail()) != null) {
            throw new EntityExistException("教师邮箱");
        }
        teacherDao.save(teacher);
    }
}
