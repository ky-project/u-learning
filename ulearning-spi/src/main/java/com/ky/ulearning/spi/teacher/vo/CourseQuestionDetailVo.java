package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseQuestionEntity}
 * 试题vo
 *
 * @author luyuhao
 * @since 2020/03/08 15:43
 */
@Data
@ApiModel("试题vo")
public class CourseQuestionDetailVo {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

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
     * 试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题
     */
    @ApiModelProperty("试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题")
    private Integer questionType;

    /**
     * 试题选项，"|#|"分隔
     */
    @ApiModelProperty("试题选项，'|#|'分隔")
    private String questionOption;


    /**
     * 试题难度 0：无级别，1：容易，2：较易，3：一般，4：较难，5：困难
     */
    @ApiModelProperty("试题难度 0：无级别，1：容易，2：较易，3：一般，4：较难，5：困难")
    private Integer questionDifficulty;

    /**
     * 每题的分数
     */
    @ApiModelProperty("每题的分数")
    private Double grade;

    /**
     * 知识模块
     */
    @ApiModelProperty("知识模块")
    private String questionKnowledge;

    /**
     * 学生答案
     */
    @ApiModelProperty(value = "学生答案")
    private String studentAnswer;

    /**
     * 参考答案
     */
    @ApiModelProperty("参考答案")
    private String questionKey;
}
