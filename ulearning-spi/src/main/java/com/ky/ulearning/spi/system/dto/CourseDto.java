package com.ky.ulearning.spi.system.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.system.entity.CourseEntity}
 * 课程dto
 *
 * @author luyuhao
 * @since 20/01/13 23:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseDto extends BaseDto {
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
    private Double courseCredit;
}
