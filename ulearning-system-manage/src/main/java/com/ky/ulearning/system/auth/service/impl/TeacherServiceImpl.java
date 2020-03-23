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
import com.ky.ulearning.spi.common.excel.TeacherExcel;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.spi.system.vo.TeacherVo;
import com.ky.ulearning.system.auth.dao.TeacherDao;
import com.ky.ulearning.system.auth.service.TeacherService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

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
        if (!StringUtil.isEmpty(newTeacher.getTeaNumber())) {
            TeacherEntity teaNumberExists = teacherDao.findByTeaNumber(newTeacher.getTeaNumber());
            if (teaNumberExists != null && !teaNumberExists.getId().equals(newTeacher.getId())) {
                throw new EntityExistException("教师编号");
            }
        }
        //判断邮箱是否存在
        if (!StringUtil.isEmpty(newTeacher.getTeaEmail())) {
            TeacherEntity emailExists = teacherDao.findByEmail(newTeacher.getTeaEmail());
            if (emailExists != null && !emailExists.getId().equals(newTeacher.getId())) {
                throw new EntityExistException("教师邮箱");
            }
        }
        //更新记录
        teacherDao.updateById(newTeacher);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
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
    @Cacheable(keyGenerator = "keyGenerator")
    public TeacherEntity getByTeaEmail(String teaEmail) {
        List<TeacherEntity> teacherEntityList = teacherDao.getListByTeaEmail(teaEmail);
        if (CollectionUtils.isEmpty(teacherEntityList)) {
            throw new BadRequestException("教师不存在");
        }
        if (teacherEntityList.size() > 1) {
            throw new BadRequestException("存在重复绑定该邮箱教师");
        }
        return teacherEntityList.get(0);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public Map<Integer, TeacherExcel> batchInsertExcel(Map<Integer, TeacherExcel> teacherExcelMap) {
        if (CollectionUtils.isEmpty(teacherExcelMap)) {
            return Collections.emptyMap();
        }
        //获取系统所有学号和邮箱
        List<String> teaNumberList = Optional.ofNullable(teacherDao.getTeaNumberList()).orElse(Collections.emptyList());
        List<String> teaEmailList = Optional.ofNullable(teacherDao.getTeaEmailList()).orElse(Collections.emptyList());
        Map<Integer, TeacherExcel> errorMap = new HashMap<>();
        List<TeacherEntity> list = new ArrayList<>();
        for (Map.Entry<Integer, TeacherExcel> teacherExcelEntry : teacherExcelMap.entrySet()) {
            try {
                //1. 转为entity
                TeacherEntity teacherEntity = new TeacherEntity();
                BeanUtils.copyProperties(teacherExcelEntry.getValue(), teacherEntity);
                //2. 属性验证
                ValidatorBuilder.build()
                        .ofNull(teacherEntity.getTeaNumber(), SystemErrorCodeEnum.TEA_NUMBER_CANNOT_BE_NULL)
                        .ofNull(teacherEntity.getTeaName(), SystemErrorCodeEnum.TEA_NAME_CANNOT_BE_NULL)
                        .on(StringUtil.isNotEmpty(teacherEntity.getTeaGender()) && teacherEntity.getTeaGender().length() > 1, SystemErrorCodeEnum.TEA_GENDER_ERROR)
                        .on(teaNumberList.contains(teacherEntity.getTeaNumber()), SystemErrorCodeEnum.TEA_NUMBER_ILLEGAL)
                        .on(StringUtil.isNotEmpty(teacherEntity.getTeaEmail()) && teaEmailList.contains(teacherEntity.getTeaEmail()), SystemErrorCodeEnum.TEA_EMAIL_ILLEGAL)
                        .doValidate().checkResult();
                //4. 设置初始密码和创建者
                teacherEntity.setTeaPassword(EncryptUtil.encryptPassword("123456"));
                teacherEntity.setCreateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
                teacherEntity.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
                //5. 存入list
                list.add(teacherEntity);
            } catch (Exception e) {
                //excel数据处理失败处理
                teacherExcelEntry.getValue().setErrorMsg(e.getMessage());
                errorMap.put(teacherExcelEntry.getKey(), teacherExcelEntry.getValue());
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            teacherDao.batchInsert(list);
        }
        return errorMap;
    }
}
