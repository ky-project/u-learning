package com.ky.ulearning.spi.monitor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * {@link com.ky.ulearning.spi.monitor.entity.LogHistoryEntity}
 * 历史日志Vo
 *
 * @author luyuhao
 * @since 20/02/12 17:07
 */
@Data
@ApiModel("历史日志Vo")
public class LogHistoryVo {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 历史日志日期
     */
    @ApiModelProperty("历史日志日期")
    private String logHistoryDate;
}
