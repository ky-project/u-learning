package com.ky.ulearning.system.auth.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师dao 接口类
 *
 * @author luyuhao
 * @date 2019/12/5 12:57
 */
@Mapper
@Repository
public interface TeacherDao {
    /**
     * 根据教师编号查询教师信息
     *
     * @param teaNumber 教师工号
     * @return 返回教师对象
     */
    TeacherEntity getByTeaNumber(@Param("teaNumber") String teaNumber);

    /**
     * 根据教师编号查询教师信息
     *
     * @param teaNumber 教师编号
     * @return 返回教师对象
     */
    TeacherEntity findByTeaNumber(String teaNumber);

    /**
     * 根据教师邮箱查询教师信息
     *
     * @param teaEmail 教师邮箱
     * @return 返回教师对象
     */
    TeacherEntity findByEmail(String teaEmail);

    /**
     * 更新教师信息
     *
     * @param teacher 待更新的教师
     */
    void updateById(TeacherDto teacher);

    /**
     * 分页查询，计算总记录数
     *
     * @param teacherDto 查询条件
     * @return 记录数
     */
    Integer countListPage(TeacherDto teacherDto);

    /**
     * 根据条件进行用户查询
     *
     * @param teacherDto 筛选条件
     * @param pageParam  分页参数
     * @return 返回教师记录集合
     */
    List<TeacherEntity> listPage(@Param("teacherDto") TeacherDto teacherDto, @Param("pageParam") PageParam pageParam);

    /**
     * 根据id更新有效值
     *
     * @param id    id
     * @param valid 有效值
     */
    void updateValidByTeaId(Long id, int valid);

    /**
     * 新增教师
     *
     * @param teacher 待添加的教师信息
     */
    void save(TeacherDto teacher);
}
