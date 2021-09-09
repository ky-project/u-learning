package com.ky.ulearning.spi.system.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.system.entity.TeachingTaskEntity}
 * 教学任务dto
 *
 * @author luyuhao
 * @since 20/01/16 00:23
 */
@Data
@ApiModel("教学任务实体类")
@EqualsAndHashCode(callSuper = true)
public class TeachingTaskDto extends BaseDto {

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
     * 教师名
     */
    @ApiModelProperty("教师名")
    private String teaName;

    /**
     * 教学任务状态 0：停用 1：启用
     */
    @ApiModelProperty("教学任务状态 0：停用 1：启用")
    private Integer taskStatus;
}
