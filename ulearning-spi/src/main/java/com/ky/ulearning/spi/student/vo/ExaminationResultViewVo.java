package com.ky.ulearning.spi.student.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 测试结果查询vo
 *
 * @author luyuhao
 * @since 2020/03/18 00:42
 */
@Data
@ApiModel("测试结果查询vo")
public class ExaminationResultViewVo {

    /**
     * 总分
     */
    @ApiModelProperty("总分")
    private Double totalScore;

    /**
     * 学生得分
     */
    @ApiModelProperty("学生总得分")
    private Double stuTotalScore;

    /**
     * 是否反馈测试结果
     */
    @ApiModelProperty("是否反馈测试结果")
    private Boolean examinationShowResult;

    /**
     * 排名
     */
    @ApiModelProperty("排名")
    private Integer ranking;

    /**
     * 提交人数
     */
    @ApiModelProperty("提交人数")
    private Integer submitNumber;

    /**
     * 测试任务名称
     */
    @ApiModelProperty("测试任务名称")
    private String examinationName;

    /**
     * 测试任务ID
     */
    @ApiModelProperty("测试任务ID")
    private Long examinationTaskId;

    /**
     * 提交时间
     */
    @ApiModelProperty("提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examiningStateSwitchTime;

    /**
     * 试题准确率
     */
    @ApiModelProperty("试题准确率")
    private Map<Integer, CourseQuestionAccuracyVo> accuracy;

    /**
     * 答题详情
     */
    @ApiModelProperty(value = "答题详情", notes = "examinationShowResult=false时，值为null")
    private Map<Integer, List<CourseQuestionViewVo>> courseQuestion;

    /**
     * 学生测试id
     */
    @ApiModelProperty(value = "学生测试id", hidden = true)
    @JsonIgnore
    private Long id;

    /**
     * 试题参数
     */
    @ApiModelProperty(value = "试题参数", hidden = true)
    @JsonIgnore
    private String examinationParameters;
}
