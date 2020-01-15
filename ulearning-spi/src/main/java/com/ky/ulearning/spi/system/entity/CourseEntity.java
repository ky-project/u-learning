package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程表实体类
 *
 * @author luyuhao
 * @since 2020/01/13 23:39
 */
@Data
@ApiModel("课程实体类")
@EqualsAndHashCode(callSuper = true)
public class CourseEntity extends BaseEntity {

    /**
     * 课程号
     */
    @ApiModelProperty("课程号")
    private String courseNumber;

    /**
     * 课程名
     */
    @ApiModelProperty("课程名")
    private String courseName;

    /**
     * 学分
     */
    @ApiModelProperty("学分")
    private Short courseCredit;
}