package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity}
 * 测试任务dto
 *
 * @author luyuhao
 * @since 20/02/13 00:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("测试任务dto")
public class ExaminationTaskDto extends BaseDto {

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
