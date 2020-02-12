package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试任务实体类
 *
 * @author luyuhao
 * @since 2020-02-13 00:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("测试任务实体类")
public class ExaminationTaskEntity extends BaseEntity {

    /**
     * 教学任务ID
     */
    private Long teachingTaskId;

    /**
     * 测试任务名称
     */
    private String examinationName;

    /**
     * 测试时长
     */
    private Short examinationDuration;

    /**
     * 任务状态
     */
    private Short examinationState;

    /**
     * 试题参数
     */
    private String examinationParameters;

    /**
     * 是否反馈测试结果
     */
    private Boolean examinationShowResult;
}