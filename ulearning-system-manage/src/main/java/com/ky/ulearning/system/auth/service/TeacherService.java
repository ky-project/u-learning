package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.spi.system.vo.TeacherVo;

import java.util.List;

/**
 * 教师service 接口类
 *
 * @author luyuhao
 * @date 2019/12/5 9:48
 */
public interface TeacherService {

    /**
     * 根据教师编号查询教师信息
     *
     * @param teaNumber 教师工号
     * @return 返回教师对象
     */
    TeacherEntity getByTeaNumber(String teaNumber);

    /**
     * 更新教师信息
     *
     * @param newTeacher 待更新的教师
     */
    void update(TeacherDto newTeacher);

    /**
     * 分页查询教师信息
     *
     * @param teacherDto 教师信息筛选条件
     * @param pageParam  分页参数
     * @return 返回分页教师类
     */
    PageBean<TeacherEntity> pageTeacherList(TeacherDto teacherDto, PageParam pageParam);

    /**
     * 删除教师
     *
     * @param id       删除id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);

    /**
     * 新增教师
     *
     * @param teacher 待添加的教师信息
     */
    void save(TeacherDto teacher);

    /**
     * 根据id查询教师信息
     *
     * @param id 教师id
     * @return 返回教师对象
     */
    TeacherEntity getById(Long id);

    /**
     * 查询所有教师信息
     *
     * @return 教师vo对象
     */
    List<TeacherVo> getAll();

    /**
     * 根据email查询教师信息
     *
     * @param teaEmail 教师邮箱
     * @return 返回教师信息
     */
    TeacherEntity getByTeaEmail(String teaEmail);
}
