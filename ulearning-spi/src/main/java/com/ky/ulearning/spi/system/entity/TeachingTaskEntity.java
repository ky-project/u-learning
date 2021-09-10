package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学任务实体类
 *
 * @author luyuhao
 * @since 2020/01/16 00:16
 */
@Data
@ApiModel("教学任务实体类")
@EqualsAndHashCode(callSuper = true)
public class TeachingTaskEntity extends BaseEntity {

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
     * 教师信息
     */
    @ApiModelProperty("教师信息")
    private TeacherEntity teacher;

    /**
     * 课程信息
     */
    @ApiModelProperty("课程信息")
    private CourseEntity course;

    /**
     * 教学任务状态 0：停用 1：启用
     */
    @ApiModelProperty("教学任务状态")
    private Boolean taskStatus;
}
