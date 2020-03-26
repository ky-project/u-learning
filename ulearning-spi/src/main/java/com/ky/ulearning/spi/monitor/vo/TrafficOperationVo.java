package com.ky.ulearning.spi.monitor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 访问操作vo
 *
 * @author luyuhao
 * @since 2020/03/27 01:45
 */
@Data
@ApiModel("访问操作vo")
public class TrafficOperationVo {

    @ApiModelProperty("总操作数")
    private List<TrafficVo> totalOperation;

    @ApiModelProperty("当前用户操作数")
    private List<TrafficVo> selfOperation;
}
