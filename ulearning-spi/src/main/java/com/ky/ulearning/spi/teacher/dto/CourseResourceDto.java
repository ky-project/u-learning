package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseResourceEntity}
 * 教学资源dto
 *
 * @author luyuhao
 * @since 20/02/17 17:51
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("教学资源dto")
@Data
public class CourseResourceDto extends BaseDto {

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String resourceTitle;

    /**
     * 概述
     */
    @ApiModelProperty("概述")
    private String resourceSummary;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private Short resourceType;

    /**
     * 是否共享
     */
    @ApiModelProperty("是否共享")
    private Boolean resourceShared;

    /**
     * 课程文件ID
     */
    @ApiModelProperty(value = "课程文件ID", hidden = true)
    private Long fileId;

    /**
     * 父节点id
     */
    @ApiModelProperty("教学资源所在文件夹id")
    private Long fileParentId;

    /**
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    private Long teachingTaskId;
}
