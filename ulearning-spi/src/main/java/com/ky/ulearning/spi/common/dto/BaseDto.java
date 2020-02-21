package com.ky.ulearning.spi.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luyuhao
 * @since 20/01/13 00:41
 */
@Data
public class BaseDto implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 是否有效
     */
    @ApiModelProperty(hidden = true)
    private Boolean valid;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String memo;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建者
     */
    @ApiModelProperty(hidden = true)
    private String createBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新者
     */
    @ApiModelProperty(hidden = true)
    private String updateBy;

    /**
     * 教师工号
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String teaNumber;

    /**
     * 学生学号
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String stuNumber;

    /**
     * 用户id
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Long userId;
}
