package com.ky.ulearning.system.auth.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.exceptions.exception.EntityExistException;
import com.ky.ulearning.common.core.utils.EncryptUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.excel.StudentExcel;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.system.auth.dao.StudentDao;
import com.ky.ulearning.system.auth.service.StudentService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
        if (stuNumberExists != null) {
            throw new EntityExistException("学号");
        }
        //判断学生邮箱是否存在
        if (StringUtil.isNotEmpty(studentDto.getStuEmail())
                && studentDao.getByStuEmail(studentDto.getStuEmail()) != null) {
            throw new EntityExistException("邮箱");
        }
        studentDao.insert(studentDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentEntity getById(Long id) {
        return studentDao.getById(id);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public PageBean<StudentEntity> pageStudentList(StudentDto studentDto, PageParam pageParam) {
        List<StudentEntity> studentList = studentDao.listPage(studentDto, pageParam);

        PageBean<StudentEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(studentDao.countListPage(studentDto))
                //设置查询结果
                .setContent(studentList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        studentDao.updateValidById(id, 0, updateBy);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentEntity getByStuNumber(String stuNumber) {
        return studentDao.getByStuNumber(stuNumber);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(StudentDto studentDto) {
        //判断学生学号是否存
        if (StringUtil.isNotEmpty(studentDto.getStuNumber())) {
            StudentEntity stuNumberExists = studentDao.getByStuNumber(studentDto.getStuNumber());
            if (stuNumberExists != null && !stuNumberExists.getId().equals(studentDto.getId())) {
                throw new EntityExistException("学号");
            }
        }
        //判断学生邮箱是否存在
        if (StringUtil.isNotEmpty(studentDto.getStuEmail())) {
            StudentEntity stuEmailExists = studentDao.getByStuEmail(studentDto.getStuEmail());
            if (stuEmailExists != null && !stuEmailExists.getId().equals(studentDto.getId())) {
                throw new EntityExistException("邮箱");
            }
        }
        studentDao.update(studentDto);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentEntity getByStuEmail(String stuEmail) {
        List<StudentEntity> studentEntityList = studentDao.getListByStuEmail(stuEmail);
        if (CollectionUtils.isEmpty(studentEntityList)) {
            throw new BadRequestException("学生不存在");
        }
        if (studentEntityList.size() > 1) {
            throw new BadRequestException("存在重复绑定该邮箱学生");
        }
        return studentEntityList.get(0);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public Map<Integer, StudentExcel> batchInsertExcel(Map<Integer, StudentExcel> studentExcelMap) {
        if (CollectionUtils.isEmpty(studentExcelMap)) {
            return Collections.emptyMap();
        }
        //获取系统所有学号和邮箱
        List<String> stuNumberList = Optional.ofNullable(studentDao.getStuNumberList()).orElse(Collections.emptyList());
        List<String> stuEmailList = Optional.ofNullable(studentDao.getStuEmailList()).orElse(Collections.emptyList());
        Map<Integer, StudentExcel> errorMap = new HashMap<>();
        List<StudentEntity> list = new ArrayList<>();
        for (Map.Entry<Integer, StudentExcel> studentExcelEntry : studentExcelMap.entrySet()) {
            try {
                //1. 转为entity
                StudentEntity studentEntity = new StudentEntity();
                BeanUtils.copyProperties(studentExcelEntry.getValue(), studentEntity);
                //2. 属性验证
                ValidatorBuilder.build()
                        .ofNull(studentEntity.getStuNumber(), SystemErrorCodeEnum.STU_NUMBER_CANNOT_BE_NULL)
                        .ofNull(studentEntity.getStuName(), SystemErrorCodeEnum.STU_NAME_CANNOT_BE_NULL)
                        .on(StringUtil.isNotEmpty(studentEntity.getStuGender()) && studentEntity.getStuGender().length() > 1, SystemErrorCodeEnum.STU_GENDER_ERROR)
                        .on(stuNumberList.contains(studentEntity.getStuNumber()), SystemErrorCodeEnum.STU_NUMBER_ILLEGAL)
                        .on(StringUtil.isNotEmpty(studentEntity.getStuEmail()) && stuEmailList.contains(studentEntity.getStuEmail()), SystemErrorCodeEnum.STU_EMAIL_ILLEGAL)
                        .doValidate().checkResult();
                //4. 设置初始密码和创建者
                studentEntity.setStuPassword(EncryptUtil.encryptPassword("123456"));
                studentEntity.setCreateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
                studentEntity.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
                //5. 存入list
                list.add(studentEntity);
            } catch (Exception e) {
                //excel数据处理失败处理
                studentExcelEntry.getValue().setErrorMsg(e.getMessage());
                errorMap.put(studentExcelEntry.getKey(), studentExcelEntry.getValue());
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            studentDao.batchInsert(list);
        }
        return errorMap;
    }
}
