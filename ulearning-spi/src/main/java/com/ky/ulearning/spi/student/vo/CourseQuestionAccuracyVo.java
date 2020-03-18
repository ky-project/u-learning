package com.ky.ulearning.spi.student.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试题准确率vo
 *
 * @author luyuhao
 * @since 2020/03/19 01:14
 */
@Data
@ApiModel("试题准确率vo")
public class CourseQuestionAccuracyVo {

    /**
     * 题目数量
     */
    @ApiModelProperty("题目数量")
    private Integer questionNumber;

    /**
     * 正确数量
     */
    @ApiModelProperty("正确数量")
    private Integer correctNumber;

    public CourseQuestionAccuracyVo() {
        this.questionNumber = 0;
        this.correctNumber = 0;
    }
}
