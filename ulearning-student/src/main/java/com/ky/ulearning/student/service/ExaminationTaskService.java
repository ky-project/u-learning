package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.dto.ExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;

import java.util.List;

/**
 * 测试任务service - 接口类
 *
 * @author luyuhao
 * @since 20/03/07 23:56
 */
public interface ExaminationTaskService {

    /**
     * 分页查询测试任务
     *
     * @param examinationTaskDto 查询参数
     * @param pageParam          分页参数
     * @return 封装测试任务的分页对象
     */
    PageBean<ExaminationTaskEntity> pageExaminationTaskList(ExaminationTaskDto examinationTaskDto, PageParam pageParam);

    /**
     * 根据id查询测试任务
     *
     * @param id 测试任务id
     * @return 返回测试任务对象
     */
    ExaminationTaskEntity getById(Long id);

    /**
     * 根据教学任务id查询测试任务数组
     *
     * @param teachingTaskId 教学任务id
     * @return 测试任务数组
     */
    List<KeyLabelVo> getExaminationTaskArr(Long teachingTaskId);
}
