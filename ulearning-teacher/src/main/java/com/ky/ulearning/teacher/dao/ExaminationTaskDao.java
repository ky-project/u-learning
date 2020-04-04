package com.ky.ulearning.teacher.dao;


import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.dto.ExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 测试任务dao
 *
 * @author luyuhao
 * @since 2020-02-13 00:50
 */
@Mapper
@Repository
public interface ExaminationTaskDao {

    /**
     * 插入测试任务记录
     *
     * @param examinationTaskDto 待插入的测试任务记录
     */
    void insert(ExaminationTaskDto examinationTaskDto);

    /**
     * 根据id查询测试任务
     *
     * @param id 测试任务id
     * @return 测试任务对象
     */
    ExaminationTaskEntity getById(Long id);

    /**
     * 更新测试任务
     *
     * @param examinationTaskDto 待更新的测试任务dto
     */
    void update(ExaminationTaskDto examinationTaskDto);

    /**
     * 分页查询测试任务
     *
     * @param examinationTaskDto 筛选参数
     * @param pageParam          分页参数
     * @return 测试任务集合
     */
    List<ExaminationTaskEntity> listPage(@Param("examinationTaskDto") ExaminationTaskDto examinationTaskDto,
                                         @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询测试任务 - 总记录数
     *
     * @param examinationTaskDto 筛选参数
     * @return 总记录数
     */
    Integer countListPage(@Param("examinationTaskDto") ExaminationTaskDto examinationTaskDto);

    /**
     * 根据id更新有效位
     *
     * @param id       测试任务id
     * @param updateBy 更新者
     * @param valid    有效位
     */
    void updateValidById(@Param("id") Long id,
                         @Param("updateBy") String updateBy,
                         @Param("valid") Integer valid);

    /**
     * 根据教学任务id查询测试任务数组
     *
     * @param teachingTaskId 教学任务id
     * @return 测试任务数组
     */
    List<KeyLabelVo> getArrByTeachingTaskId(Long teachingTaskId);

    /**
     * 根据id查询组卷参数
     *
     * @param id id
     * @return 组卷参数
     */
    String getExaminationParameters(Long id);

    /**
     * 根据教师id查询进行中的测试任务数量
     *
     * @param teaId 教师id
     * @return 测试任务数量
     */
    Integer getExaminationNumber(Long teaId);
}
