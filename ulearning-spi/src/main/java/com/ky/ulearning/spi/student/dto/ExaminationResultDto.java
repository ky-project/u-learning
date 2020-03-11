package com.ky.ulearning.spi.student.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.student.entity.ExaminationResultEntity}
 * 测试结果dto
 *
 * @author luyuhao
 * @since 2020/03/11 01:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("测试结果dto")
public class ExaminationResultDto extends BaseDto {

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
    @ApiModelProperty(value = "得分",hidden = true)
    private Double studentScore;
}
