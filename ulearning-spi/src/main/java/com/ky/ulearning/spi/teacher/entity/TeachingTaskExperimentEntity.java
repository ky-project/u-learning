package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实验实体类
 *
 * @author luyuhao
 * @since 2020/02/04 20:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("实验实体类")
public class TeachingTaskExperimentEntity extends BaseEntity {

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
}