package com.ky.ulearning.spi.student.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生测试基本信息vo
 *
 * @author luyuhao
 * @since 2020/03/18 01:17
 */
@Data
@ApiModel("学生测试基本信息vo")
public class StudentExaminationTaskBaseInfoVo {

    /**
     * 学生测试id
     */
    @ApiModelProperty("学生测试id")
    private Long id;

    /**
     * 学生得分
     */
    @ApiModelProperty("学生总得分")
    private Double stuTotalScore;
}
