package com.ky.ulearning.spi.student.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试结果实体类
 *
 * @author luyuhao
 * @since 2020/03/11 01:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("测试结果实体类")
public class ExaminationResultEntity extends BaseEntity {

    /**
     * 试题ID
     */
    @ApiModelProperty("试题ID")
    private Long questionId;

    /**
     * 测试ID
     */
    @ApiModelProperty("测试ID")
    private Long examiningId;

    /**
     * 学生答案
     */
    @ApiModelProperty("学生答案")
    private String studentAnswer;

    /**
     * 得分
     */
    @ApiModelProperty("得分")
    private Double studentScore;
}
