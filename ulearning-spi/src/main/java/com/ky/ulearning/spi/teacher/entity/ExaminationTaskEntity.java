package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
}
