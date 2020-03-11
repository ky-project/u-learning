package com.ky.ulearning.spi.student.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.student.entity.ExperimentResultEntity}
 * 实验结果dto
 *
 * @author luyuhao
 * @since 20/03/06 01:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("实验结果dto")
public class ExperimentResultDto extends BaseDto {

    /**
     * 实验ID
     */
    @ApiModelProperty("实验ID")
    private Long experimentId;

    /**
     * 学生id
     */
    @ApiModelProperty(value = "学生id")
    private Long stuId;

    /**
     * 已提交
     */
    @ApiModelProperty(value = "已提交", hidden = true)
    private Boolean experimentCommitState;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private Date experimentCommitTime;

    /**
     * 实验内容
     */
    @ApiModelProperty("实验内容")
    private String experimentResult;

    /**
     * 成果附件
     */
    @ApiModelProperty("成果附件")
    @JsonIgnore
    private String experimentUrl;

    /**
     * 成绩
     */
    @ApiModelProperty("成绩")
    private Double experimentScore;

    /**
     * 教师反馈
     */
    @ApiModelProperty("教师反馈")
    private String experimentAdvice;

    /**
     * 附件名
     */
    @ApiModelProperty("附件名")
    private String experimentAttachmentName;

    /**
     * 是否共享分享展示 0：否 1：是
     */
    @ApiModelProperty("是否共享分享展示")
    private Boolean experimentShared;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小", hidden = true)
    private Long experimentAttachmentSize;

    /**
     * 已批改
     */
    @ApiModelProperty("已批改")
    private Boolean isCorrected;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String stuNumber;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String stuName;
}
