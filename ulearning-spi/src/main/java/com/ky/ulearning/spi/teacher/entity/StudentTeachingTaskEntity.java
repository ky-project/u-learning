package com.ky.ulearning.spi.teacher.entity;

import java.util.Date;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生选课实体类
 *
 * @author luyuhao
 * @since 2020/01/30 00:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentTeachingTaskEntity extends BaseEntity {

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