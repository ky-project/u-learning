package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * {@link com.ky.ulearning.spi.student.entity.ExaminationResultEntity}
 * 测试结果vo
 *
 * @author luyuhao
 * @since 2020/03/11 01:08
 */
@Data
@ApiModel("测试结果vo")
public class ExaminationResultDetailVo {

    /**
     * 剩余时间
     */
    @ApiModelProperty("剩余时间")
    private Integer examiningRemainTime;

    /**
     * 测试任务名称
     */
    @ApiModelProperty("测试任务名称")
    private String examinationName;

    /**
     * 组卷题目
     */
    @ApiModelProperty("组卷题目")
    private Map<Integer, List<CourseQuestionDetailVo>> courseQuestion;
}
