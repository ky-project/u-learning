package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
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
 * @since 20/03/07 23:56
 */
@Mapper
@Repository
public interface ExaminationTaskDao {


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
     * 根据id查询测试任务
     *
     * @param id 测试任务id
     * @return 测试任务对象
     */
    ExaminationTaskEntity getById(Long id);
}
