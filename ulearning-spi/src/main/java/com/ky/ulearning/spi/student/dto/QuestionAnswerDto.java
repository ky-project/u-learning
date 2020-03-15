package com.ky.ulearning.spi.student.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测试结果基本属性dto
 *
 * @author luyuhao
 * @since 2020/03/15 21:30
 */
@Data
@ApiModel("测试结果基本dto")
public class QuestionAnswerDto {
    /**
     * 试题id
     */
    @ApiModelProperty("试题id")
    private Long questionId;

    /**
     * 试题id对应的学生答案
     */
    @ApiModelProperty("试题id对应的学生答案")
    private String studentAnswer;
}
