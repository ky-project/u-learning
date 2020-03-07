package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity}
 * {@link com.ky.ulearning.spi.system.entity.TeachingTaskEntity}
 * 实验dto
 *
 * @author luyuhao
 * @since 20/02/05 18:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("实验dto")
public class TeachingTaskExperimentDto extends BaseDto {

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
     * 教学任务别称
     */
    @ApiModelProperty("教学任务别称")
    private String teachingTaskAlias;

    /**
     * 开课学期
     */
    @ApiModelProperty("开课学期")
    private String term;

    /**
     * 附件大小
     */
    @ApiModelProperty(value = "附件大小", hidden = true)
    private Long experimentAttachmentSize;
}
