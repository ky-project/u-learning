package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学资源实体类
 *
 * @author luyuhao
 * @since 2020/02/17 17:50
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("教学资源实体类")
@Data
public class CourseResourceEntity extends BaseEntity {

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
}