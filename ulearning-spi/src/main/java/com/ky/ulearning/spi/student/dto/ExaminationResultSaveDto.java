package com.ky.ulearning.spi.student.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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
     * 试题答案dto集合
     */
    @ApiModelProperty("试题答案dto集合")
    List<QuestionAnswerDto> questionAnswerDtoList;

    /**
     * 测试任务id
     */
    @ApiModelProperty("测试任务id")
    private Long examinationTaskId;

    /**
     * 学生测试ID
     */
    @ApiModelProperty(value = "学生测试ID", hidden = true)
    private Long examiningId;

    /**
     * 是否交卷
     */
    @ApiModelProperty("是否交卷")
    private Boolean isSubmit;
}
