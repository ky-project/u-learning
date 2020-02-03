package com.ky.ulearning.spi.monitor.logging.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.monitor.logging.entity.LogEntity}
 * 日志dto
 *
 * @author luyuhao
 * @since 20/01/13 00:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogDto extends BaseDto {
    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String logUsername;

    /**
     * 操作
     */
    @ApiModelProperty("操作")
    private String logDescription;

    /**
     * 执行模块
     */
    @ApiModelProperty("执行模块")
    private String logModule;

    /**
     * 用户ip
     */
    @ApiModelProperty("用户ip")
    private String logIp;

    /**
     * 日志类型
     */
    @ApiModelProperty("日志类型")
    private String logType;

    /**
     * 异常详细
     */
    @ApiModelProperty("异常详细")
    private String logException;

    /**
     * 请求参数
     */
    @ApiModelProperty("请求参数")
    private String logParams;

    /**
     * 请求耗时
     */
    @ApiModelProperty("请求耗时")
    private Long logTime;

    /**
     * 操作地址
     */
    @ApiModelProperty("操作地址")
    private String logAddress;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
