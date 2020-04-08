package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseFileEntity}
 * {@link com.ky.ulearning.spi.teacher.entity.CourseResourceEntity}
 * 课程文件&教学资源dto
 *
 * @author luyuhao
 * @since 20/02/17 22:51
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("课程文件&教学资源dto")
@Data
public class CourseFileResourceDto extends BaseDto {

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
    @ApiModelProperty("课程文件ID")
    private Long fileId;

    /**
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    private Long teachingTaskId;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String fileName;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小", hidden = true)
    private Long fileSize;

    /**
     * 文件后缀名
     */
    @ApiModelProperty("文件后缀名")
    private String fileExt;

    /**
     * 文件类型 1：文件 2：文件夹
     */
    @ApiModelProperty("文件类型 1：文件 2：文件夹")
    private Integer fileType;

    /**
     * 文件所属文件夹 0为根目录
     */
    @ApiModelProperty("文件所属文件夹 0为根目录")
    private Long fileParentId;

    /**
     * 能否删除分享的文件
     */
    @ApiModelProperty("能否删除分享的文件 0：否 1：是")
    private Integer canDeleteSharedFile;
}
