package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseDocumentationEntity}
 * 文件资料dto
 *
 * @author luyuhao
 * @since 20/02/14 20:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("文件资料dto")
public class CourseDocumentationDto extends BaseDto {
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
