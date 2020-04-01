package com.ky.ulearning.spi.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽取每个实体类必须字段
 *
 * @author luyuhao
 * @date 19/12/08 03:39
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
    /**
     * 是否有效
     */
    @ApiModelProperty("是否有效")
    private Boolean valid;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String memo;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
}
