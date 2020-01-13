package com.ky.ulearning.spi.monitor.logging.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 日志实体类
 *
 * @author luyuhao
 * @date 19/12/05 02:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogEntity extends BaseEntity {

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
     * 具体操作内容
     */
    @ApiModelProperty("具体操作内容")
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
}
