package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity}
 * 学生选课dto
 *
 * @author luyuhao
 * @since 2020/01/30 00:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentTeachingTaskDto extends BaseDto {

    /**
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    private Long teachingTaskId;

    /**
     * 学生id
     */
    @ApiModelProperty("学生id")
    private Long stuId;

}