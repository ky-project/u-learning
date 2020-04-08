package com.ky.ulearning.spi.teacher.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseFileEntity}
 * {@link com.ky.ulearning.spi.teacher.entity.CourseDocumentationEntity}
 * 课程文件&文件资料dto
 *
 * @author luyuhao
 * @since 20/02/16 14:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("课程文件&文件资料dto")
public class CourseFileDocumentationDto extends BaseDto {
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String documentationTitle;

    /**
     * 摘要
     */
    @ApiModelProperty("摘要")
    private String documentationSummary;

    /**
     * 文件资料分类
     */
    @ApiModelProperty("文件资料分类")
    private Short documentationCategory;

    /**
     * 是否共享
     */
    @ApiModelProperty("是否共享")
    private Boolean documentationShared;

    /**
     * 课程文件ID
     */
    @ApiModelProperty(value = "课程文件ID")
    private Long fileId;

    /**
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    @JsonIgnore
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
