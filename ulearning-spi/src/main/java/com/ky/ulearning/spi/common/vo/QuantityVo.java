package com.ky.ulearning.spi.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 题目信息vo
 *
 * @author luyuhao
 * @since 20/03/07 23:28
 */
@Data
@ApiModel("题目信息vo")
public class QuantityVo {
    /**
     * 试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题
     */
    @ApiModelProperty("试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题")
    private Integer questionType;

    /**
     * 题目数量
     */
    @ApiModelProperty("题目数量")
    private Integer amount;

    /**
     * 每题的分数
     */
    @ApiModelProperty("每题的分数")
    private Double grade;
}
