package com.ky.ulearning.spi.monitor.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 历史日志实体类
 *
 * @author luyuhao
 * @since 2020/02/10 20:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("历史日志实体类")
public class LogHistoryEntity extends BaseEntity {

    /**
     * 历史日志日期
     */
    @ApiModelProperty("历史日志日期")
    private String logHistoryDate;

    /**
     * 历史日志文件名
     */
    @ApiModelProperty("历史日志文件名")
    private String logHistoryName;

    /**
     * 历史日志url
     */
    @ApiModelProperty("历史日志url")
    private String logHistoryUrl;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Long logHistorySize;
}