package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.spi.system.vo.TeacherVo;
import com.ky.ulearning.system.auth.dao.TeacherDao;
import com.ky.ulearning.system.auth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
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
public class TeacherServiceImpl extends BaseService implements TeacherService {

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
        if(!StringUtil.isEmpty(newTeacher.getTeaNumber())) {
            TeacherEntity teaNumberExists = teacherDao.findByTeaNumber(newTeacher.getTeaNumber());
            if (teaNumberExists != null && !teaNumberExists.getId().equals(newTeacher.getId())) {
                throw new EntityExistException("教师编号");
            }
        }
        //判断邮箱是否存在
        if(!StringUtil.isEmpty(newTeacher.getTeaEmail())) {
            TeacherEntity emailExists = teacherDao.findByEmail(newTeacher.getTeaEmail());
            if (emailExists != null && !emailExists.getId().equals(newTeacher.getId())) {
                throw new EntityExistException("教师邮箱");
            }
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
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        teacherDao.updateValidByTeaId(id, 0, updateBy);
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

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeacherEntity getById(Long id) {
        return teacherDao.getById(id);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<TeacherVo> getAll() {
        return teacherDao.getAllVo();
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void updateLastLoginTime(TeacherDto teacherDto) {
        teacherDao.updateLastLoginTime(teacherDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void updateTeaPhoto(TeacherDto teacherDto) {
        teacherDao.updateTeaPhoto(teacherDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void updateUpdateTime(Long id, Date updateTime) {
        teacherDao.updateUpdateTime(id, updateTime);
    }
}
