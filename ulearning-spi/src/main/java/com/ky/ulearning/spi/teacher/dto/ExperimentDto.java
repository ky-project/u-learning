package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity}
 * 实验dto
 *
 * @author luyuhao
 * @since 20/02/04 20:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("实验dto")
public class ExperimentDto extends BaseDto {
    /**
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    private Long teachingTaskId;

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private Integer experimentOrder;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String experimentTitle;

    /**
     * 内容要求
     */
    @ApiModelProperty("内容要求")
    private String experimentContent;

    /**
     * 附件URL
     */
    @ApiModelProperty("附件URL")
    private String experimentAttachment;

    /**
     * 附件名
     */
    @ApiModelProperty("附件名")
    private String experimentAttachmentName;

    /**
     * 实验结果 0：未提交 1：已提交 2：已批改
     */
    @ApiModelProperty("实验结果 0：未提交 1：已提交 2：已批改")
    private Integer experimentStatus;

    /**
     * 是否共享
     */
    @ApiModelProperty("是否共享")
    private Boolean experimentShared;
}
