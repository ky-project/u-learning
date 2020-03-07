package com.ky.ulearning.spi.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 测试参数vo
 *
 * @author luyuhao
 * @since 20/03/07 22:47
 */
@ApiModel("测试参数vo")
@Data
public class ExaminationParamVo {

    /**
     * 知识点
     */
    @ApiModelProperty("知识点")
    private List<KeyLabelVo> questionKnowledges;

    /**
     * 测试难度
     */
    @ApiModelProperty("测试难度")
    private Integer questionDifficulty;

    /**
     * 题型信息集合
     */
    @ApiModelProperty("题型信息集合")
    private List<QuantityVo> quantity;
}
