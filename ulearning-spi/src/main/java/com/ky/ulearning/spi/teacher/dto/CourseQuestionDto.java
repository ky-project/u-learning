package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseQuestionEntity}
 * 试题dto
 *
 * @author luyuhao
 * @since 20/02/03 19:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("试题dto")
public class CourseQuestionDto extends BaseDto {
    /**
     * 课程ID
     */
    @ApiModelProperty("课程ID")
    private Long courseId;

    /**
     * 试题内容
     */
    @ApiModelProperty("试题内容")
    private String questionText;

    /**
     * 图片URL
     */
    @ApiModelProperty("图片URL")
    private String questionUrl;

    /**
     * 参考答案
     */
    @ApiModelProperty("参考答案")
    private String questionKey;

    /**
     * 知识模块
     */
    @ApiModelProperty("知识模块")
    private String questionKnowledge;

    /**
     * 试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题
     */
    @ApiModelProperty("试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题")
    private Integer questionType;
}
