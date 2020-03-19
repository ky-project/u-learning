package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 考试状态统计
 *
 * @author luyuhao
 * @since 2020/03/20 01:22
 */
@Data
@ApiModel("考试状态统计")
public class ExaminationStatusVo {
    /**
     * 考试状态 1：未开始 2：进行中 3：已结束
     */
    @ApiModelProperty("考试状态 0：未开始 1：进行中 2：已结束")
    private Integer resultType;

    /**
     * 学生人数
     */
    @ApiModelProperty("学生人数")
    private Integer stuNumber;

    public ExaminationStatusVo() {
    }

    public ExaminationStatusVo(Integer resultType, Integer stuNumber) {
        this.resultType = resultType;
        this.stuNumber = stuNumber;
    }
}
