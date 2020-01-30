package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 教学任务dto
 *
 * @author luyuhao
 * @since 20/01/26 16:16
 */
@Mapper
@Repository
public interface TeachingTaskDao {
    /**
     * 分页查询课程&教学任务信息
     *
     * @param pageParam             分页参数
     * @param courseTeachingTaskDto 筛选条件
     * @return 课程&教学任务集合
     */
    List<CourseTeachingTaskDto> listPage(@Param("courseTeachingTaskDto") CourseTeachingTaskDto courseTeachingTaskDto, @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询课程&教学任务信息-总记录数
     *
     * @param courseTeachingTaskDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("courseTeachingTaskDto") CourseTeachingTaskDto courseTeachingTaskDto);

    /**
     * 根据教师id、课程id、学期和别称查询教学任务信息
     *
     * @param teachingTaskDto 查询条件参数对象
     * @return 教学任务对象
     */
    TeachingTaskEntity getByTeaIdAndCourseIdAndTermAndAlias(TeachingTaskDto teachingTaskDto);

    /**
     * 插入教学任务信息
     *
     * @param teachingTaskDto 教学任务信息
     */
    void insert(TeachingTaskDto teachingTaskDto);

    /**
     * 更新教学任务信息
     *
     * @param teachingTaskDto 教学任务对象
     */
    void update(TeachingTaskDto teachingTaskDto);

    /**
     * 根据教师id查询所有教学任务id集合
     *
     * @param teaId 教师id
     * @return 教学任务id集合
     */
    Set<Long> getIdByTeaId(Long teaId);

    /**
     * 根据教学任务id查询课程教学任务信息
     *
     * @param id 教学任务id
     * @return 返回课程教学任务对象
     */
    CourseTeachingTaskDto getById(Long id);
}
