package com.ky.ulearning.spi.student.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity}
 * {@link com.ky.ulearning.spi.system.entity.TeachingTaskEntity}
 * 学生实验dto
 *
 * @author luyuhao
 * @since 20/03/06 23:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("学生实验dto")
public class StudentTeachingTaskExperimentDto extends BaseDto {

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
    @JsonIgnore
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
     * 是否已提交实验结果
     */
    @ApiModelProperty("实验结果 0：未提交 1：已提交 2：已批改")
    private Integer experimentStatus;

    /**
     * 附件大小
     */
    @ApiModelProperty(value = "附件大小", hidden = true)
    private Long experimentAttachmentSize;
}
