package com.ky.ulearning.spi.teacher.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 学生测试结果vo
 *
 * @author luyuhao
 * @since 2020/03/20 23:13
 */
@Data
@ApiModel("学生测试结果vo")
public class StudentExaminationResultVo {

    /**
     * 学生测试id
     */
    @ApiModelProperty(value = "学生测试id", hidden = true)
    private Long examiningId;

    /**
     * 测试任务id
     */
    @ApiModelProperty("测试任务id")
    @JsonIgnore
    private Long examinationTaskId;

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

    /**
     * 系部
     */
    @ApiModelProperty("系部")
    private String stuDept;

    /**
     * 专业班级
     */
    @ApiModelProperty("专业班级")
    private String stuClass;

    /**
     * 成绩
     */
    @ApiModelProperty("成绩")
    private Double stuScore;

    /**
     * 准确率
     */
    @ApiModelProperty("准确率")
    private Double accuracy;

    /**
     * 排名
     */
    @ApiModelProperty("排名")
    private Integer ranking;

    /**
     * 提交时间
     */
    @ApiModelProperty("提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examiningStateSwitchTime;

    /**
     * 测试任务名称
     */
    @ApiModelProperty("测试任务名称")
    private String examinationName;

}
