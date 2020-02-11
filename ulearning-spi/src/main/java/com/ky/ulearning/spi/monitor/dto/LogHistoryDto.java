package com.ky.ulearning.spi.monitor.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.monitor.entity.LogHistoryEntity}
 * 历史日志dto
 *
 * @author luyuhao
 * @since 20/02/10 20:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("历史日志实体类")
public class LogHistoryDto extends BaseDto {
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
