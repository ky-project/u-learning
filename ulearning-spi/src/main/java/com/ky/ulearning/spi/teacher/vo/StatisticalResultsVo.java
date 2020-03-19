package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统计结果
 *
 * @author luyuhao
 * @since 2020/03/20 01:23
 */
@Data
@ApiModel("统计结果")
public class StatisticalResultsVo {

    /**
     * 结果类型 1：不及格 2：及格 3：良好 4：优秀
     */
    @ApiModelProperty("结果类型 1：不及格 2：及格 3：良好 4：优秀")
    private Integer resultType;

    /**
     * 学生人数
     */
    @ApiModelProperty("学生人数")
    private Integer stuNumber;

    public StatisticalResultsVo() {
    }

    public StatisticalResultsVo(Integer resultType, Integer stuNumber) {
        this.resultType = resultType;
        this.stuNumber = stuNumber;
    }
}
