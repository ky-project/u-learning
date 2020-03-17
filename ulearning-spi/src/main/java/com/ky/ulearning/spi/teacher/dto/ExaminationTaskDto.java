package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("教学任务ID")
    private Long teachingTaskId;

    /**
     * 测试任务名称
     */
    @ApiModelProperty("测试任务名称")
    private String examinationName;

    /**
     * 测试时长
     */
    @ApiModelProperty("测试时长（分钟）")
    private Integer examinationDuration;

    /**
     * 任务状态
     */
    @ApiModelProperty("任务状态，1：未发布 2：未开始 3：进行中 4：已结束")
    private Integer examinationState;

    /**
     * 试题参数
     */
    @ApiModelProperty("试题参数")
    private String examinationParameters;

    /**
     * 是否反馈测试结果
     */
    @ApiModelProperty("是否反馈测试结果")
    private Boolean examinationShowResult;

    /**
     * 测试状态
     */
    @ApiModelProperty(value = "测试状态 1：进行中 2：已完成", hidden = true)
    private Integer examiningState;
}
