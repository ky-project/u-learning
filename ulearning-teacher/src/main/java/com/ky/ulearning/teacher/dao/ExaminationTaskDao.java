package com.ky.ulearning.teacher.dao;


import com.ky.ulearning.spi.teacher.dto.ExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

}