package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.system.entity.TeachingTaskEntity}
 * {@link com.ky.ulearning.spi.system.entity.CourseEntity}
 * 课程-教学任务dto
 * @author luyuhao
 * @since 20/01/26 16:02
 */
@Data
@ApiModel("课程&教学任务实体类")
@EqualsAndHashCode(callSuper = true)
public class CourseTeachingTaskDto extends BaseDto {
    /**
     * 课程ID
     */
    @ApiModelProperty("课程ID")
    private Long courseId;

    /**
     * 教师id
     */
    @ApiModelProperty("教师id")
    private Long teaId;

    /**
     * 教学任务别称
     */
    @ApiModelProperty("教学任务别称")
    private String teachingTaskAlias;

    /**
     * 开课学期
     */
    @ApiModelProperty("开课学期")
    private String term;

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
