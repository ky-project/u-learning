package com.ky.ulearning.spi.teacher.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseQuestionEntity}
 * 课程试题dto
 *
 * @author luyuhao
 * @since 20/02/03 19:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("课程试题dto")
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
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    @JsonIgnore
    private Long teachingTaskId;

    /**
     * 课程号
     */
    @ApiModelProperty("课程号")
    private String courseNumber;

    /**
     * 课程名
     */
    @ApiModelProperty("课程名")
    private String courseName;

    /**
     * 学分
     */
    @ApiModelProperty("学分")
    private Short courseCredit;
}
