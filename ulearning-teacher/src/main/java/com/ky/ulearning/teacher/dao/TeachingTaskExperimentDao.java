package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实验dao
 *
 * @author luyuhao
 * @since 2020/02/04 20:53
 */
@Mapper
@Repository
public interface TeachingTaskExperimentDao {

    /**
     * 插入实验
     *
     * @param experimentDto 待插入实验对象
     */
    void insert(ExperimentDto experimentDto);

    /**
     * 根据id查询实验信息
     *
     * @param id 实验id
     * @return 实验对象
     */
    TeachingTaskExperimentDto getById(Long id);

    /**
     * 更新实验信息
     *
     * @param experimentDto 待更新的实验对象
     */
    void update(ExperimentDto experimentDto);

    /**
     * 分页查询实验信息
     *
     * @param experimentDto 筛选条件
     * @param pageParam     分页参数
     * @return 返回实验信息集合
     */
    List<TeachingTaskExperimentDto> listPage(@Param("experimentDto") ExperimentDto experimentDto,
                                             @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询实验信息 - 总记录数
     *
     * @param experimentDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("experimentDto") ExperimentDto experimentDto);

    /**
     * 根据教学任务id查询试验集合
     *
     * @param teachingTaskId 教学任务id
     * @return 试验信息集合
     */
    List<TeachingTaskExperimentEntity> listByTeachingTaskId(Long teachingTaskId);

    /**
     * 根据教学任务id查询试验dto集合
     *
     * @param teachingTaskId 教学任务id
     * @return 实验dto集合
     */
    List<ExperimentDto> listDtoByTeachingTaskId(Long teachingTaskId);

    /**
     * 删除实验
     *
     * @param id       实验id
     * @param updateBy 更新者
     */
    void delete(@Param("id") Long id, @Param("updateBy") String updateBy);
}
