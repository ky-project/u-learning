package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件资料实体类
 *
 * @author luyuhao
 * @since 2020/02/14 20:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("文件资料实体类")
public class CourseDocumentationEntity extends BaseEntity {

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
    @ApiModelProperty("课程文件ID")
    private Long fileId;
}