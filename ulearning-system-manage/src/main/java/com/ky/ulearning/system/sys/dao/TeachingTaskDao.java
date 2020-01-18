package com.ky.ulearning.system.sys.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教学任务 dao
 *
 * @author luyuhao
 * @since 2020/01/16 00:16
 */
@Mapper
@Repository
public interface TeachingTaskDao {

    /**
     * 插入教学任务信息
     *
     * @param teachingTaskDto 教学任务信息
     */
    void insert(TeachingTaskDto teachingTaskDto);

    void update(TeachingTaskDto teachingTaskDto);

    /**
     * 根据教学任务别称查询教学任务信息
     *
     * @param teachingTaskAlias 教学任务别称
     * @return 返回教学任务实体类
     */
    TeachingTaskEntity getByAlias(String teachingTaskAlias);

    /**
     * 根据教师id、课程id、学期和别称查询教学任务信息
     *
     * @param teachingTaskDto 查询条件参数对象
     * @return 教学任务对象
     */
    TeachingTaskEntity getByTeaIdAndCourseIdAndTermAndAlias(TeachingTaskDto teachingTaskDto);

    /**
     * 分页查询教学任务信息
     *
     * @param teachingTaskDto 筛选参数
     * @param pageParam       分页参数
     * @return 教学任务信息集合
     */
    List<TeachingTaskEntity> listPage(@Param("teachingTaskDto") TeachingTaskDto teachingTaskDto, @Param("pageParam") PageParam pageParam);

    /**
     * 计算分页查询总记录数
     *
     * @param teachingTaskDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("teachingTaskDto") TeachingTaskDto teachingTaskDto);
}