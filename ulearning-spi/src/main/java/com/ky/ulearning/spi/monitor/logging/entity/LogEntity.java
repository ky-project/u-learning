package com.ky.ulearning.spi.monitor.logging.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 日志实体类
 *
 * @author luyuhao
 * @date 19/12/05 02:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity {
    /**
     * 日志ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String logUsername;

    /**
     * 操作
     */
    private String logDescription;

    /**
     * 执行模块
     */
    private String logModule;

    /**
     * 用户ip
     */
    private String logIp;

    /**
     * 具体操作内容
     */
    private String logType;

    /**
     * 异常详细
     */
    private String logException;

    /**
     * 请求参数
     */
    private String logParams;

    /**
     * 请求耗时
     */
    private Long logTime;

    /**
     * 操作地址
     */
    private String logAddress;

    /**
     * 是否有效
     */
    private Boolean valid;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;
}
