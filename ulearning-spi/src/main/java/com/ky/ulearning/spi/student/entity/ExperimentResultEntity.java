package com.ky.ulearning.spi.student.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实验结果实体类
 *
 * @author luyuhao
 * @since 2020/03/06 01:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("实验结果实体类")
public class ExperimentResultEntity extends BaseEntity {

    /**
	* 实验ID
	*/
    @ApiModelProperty("实验ID")
    private Long experimentId;

    /**
	* 学生id
	*/
    @ApiModelProperty("学生id")
    private Long stuId;

    /**
	* 已提交
	*/
    @ApiModelProperty("已提交")
    private Boolean experimentCommitState;

    /**
	* 提交时间
	*/
    @ApiModelProperty("提交时间")
    private Date experimentCommitTime;

    /**
	* 实验结果
	*/
    @ApiModelProperty("实验结果")
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
}
