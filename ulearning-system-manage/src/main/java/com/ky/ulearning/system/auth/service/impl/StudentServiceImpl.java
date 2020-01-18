package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.system.auth.dao.StudentDao;
import com.ky.ulearning.system.auth.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生service - 实现类
 *
 * @author luyuhao
 * @since 20/01/18 22:54
 */
@Service
@CacheConfig(cacheNames = "student")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentServiceImpl extends BaseService implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(StudentDto studentDto) {
        //判断学生学号是否存
        StudentEntity stuNumberExists = studentDao.getByStuNumber(studentDto.getStuNumber());
        if(stuNumberExists != null){
            throw new EntityExistException("学号");
        }
        //判断学生邮箱是否存在
        if(StringUtil.isNotEmpty(studentDto.getStuEmail())
                && studentDao.getByStuEmail(studentDto.getStuEmail()) != null) {
            throw new EntityExistException("邮箱");
        }
        studentDao.insert(studentDto);
    }
}
