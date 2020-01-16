package com.ky.ulearning.spi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * {@link com.ky.ulearning.spi.system.entity.CourseEntity}
 *
 * @author luyuhao
 * @since 20/01/17 01:56
 */
@Data
@ApiModel("课程Vo对象")
public class CourseVo implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

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
