package com.ky.ulearning.spi.student.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测试结果保存dto
 *
 * @author luyuhao
 * @since 2020/03/12 01:58
 */
@ApiModel("测试结果保存dto")
@Data
public class ExaminationResultSaveDto {
    /**
     * 试题ids，'|#|'分隔
     */
    @ApiModelProperty("试题ids，'|#|'分隔")
    private String questionIds;

    /**
     * 试题id对应的学生答案，'|#|'分隔
     */
    @ApiModelProperty("试题id对应的学生答案，'|#|'分隔")
    private String studentAnswers;

    /**
     * 测试任务id
     */
    @ApiModelProperty("测试任务id")
    private Long examinationTaskId;

    /**
     * 测试ID
     */
    @ApiModelProperty(value = "测试ID",hidden = true)
    private Long examiningId;
}
