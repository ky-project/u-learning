package com.ky.ulearning.spi.student.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity}
 * 学生测试dto
 *
 * @author luyuhao
 * @since 20/03/08 00:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("学生测试dto")
public class StudentExaminationTaskDto extends BaseDto {

    /**
     * 测试任务ID
     */
    @ApiModelProperty("测试任务ID")
    private Long examinationTaskId;

    /**
     * 学生id
     */
    @ApiModelProperty("学生id")
    private Long stuId;

    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String examiningHostIp;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examiningLoginTime;

    /**
     * 剩余时间
     */
    @ApiModelProperty("剩余时间")
    private Integer examiningRemainTime;

    /**
     * 测试状态
     */
    @ApiModelProperty("测试状态 1：进行中 2：已完成")
    private Integer examiningState;

    /**
     * 状态变更时间
     */
    @ApiModelProperty("状态变更时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date examiningStateSwitchTime;

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
     * 性别
     */
    @ApiModelProperty("性别")
    private String stuGender;

    /**
     * 专业班级
     */
    @ApiModelProperty("专业班级")
    private String stuClass;
}
