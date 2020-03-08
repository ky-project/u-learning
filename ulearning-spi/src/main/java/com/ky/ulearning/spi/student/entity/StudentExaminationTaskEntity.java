package com.ky.ulearning.spi.student.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 学生测试实体类
 *
 * @author luyuhao
 * @since 2020/03/08 00:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("学生测试实体类")
public class StudentExaminationTaskEntity extends BaseEntity {

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
}
