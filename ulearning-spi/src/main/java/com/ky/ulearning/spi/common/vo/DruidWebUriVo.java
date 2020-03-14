package com.ky.ulearning.spi.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * druidURI监控vo
 *
 * @author luyuhao
 * @since 2020/3/14 11:04
 */
@Data
@ApiModel("druidURI监控vo")
public class DruidWebUriVo {

    @ApiModelProperty("uri")
    private String uri;

    @ApiModelProperty("执行中")
    private Integer runningCount;

    @ApiModelProperty("最大并发")
    private Integer concurrentMax;

    @ApiModelProperty("请求次数")
    private Integer requestCount;

    @ApiModelProperty("请求时间（和）")
    private Long requestTimeMillis;

    @ApiModelProperty("出错数")
    private Integer errorCount;

    @ApiModelProperty("最后一次请求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastAccessTime;

    @ApiModelProperty("事务提交数")
    private Integer jdbcCommitCount;

    @ApiModelProperty("事务回滚数")
    private Integer jdbcRollbackCount;

    @ApiModelProperty("jdbc执行数")
    private Long jdbcExecuteCount;

    @ApiModelProperty("jdbc出错数")
    private Long jdbcExecuteErrorCount;

    @ApiModelProperty("jdbc执行高峰")
    private Long jdbcExecutePeak;

    @ApiModelProperty("jdbc时间")
    private Long jdbcExecuteTimeMillis;

    @ApiModelProperty("读取行数")
    private Long jdbcFetchRowCount;

    @ApiModelProperty("读取行数高峰")
    private Long jdbcFetchRowPeak;

    @ApiModelProperty("更新行数")
    private Long jdbcUpdateCount;

    @ApiModelProperty("更新行数高峰")
    private Long jdbcUpdatePeak;

    @ApiModelProperty("连接池连接打开数")
    private Long jdbcPoolConnectionOpenCount;

    @ApiModelProperty("连接池连接关闭数")
    private Long jdbcPoolConnectionCloseCount;

    @ApiModelProperty("结果集打开数")
    private Long jdbcResultSetOpenCount;

    @ApiModelProperty("结果集关闭数")
    private Long jdbcResultSetCloseCount;

    @ApiModelProperty("区间分布")
    private String histogram;

    @ApiModelProperty("资料")
    private String profiles;

    @ApiModelProperty("请求最慢（单次）")
    private Long requestTimeMillisMax;

    @ApiModelProperty("请求最慢发生时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date requestTimeMillisMaxOccurTime;
}
