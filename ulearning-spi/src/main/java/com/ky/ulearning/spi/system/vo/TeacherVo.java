package com.ky.ulearning.spi.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * {@link com.ky.ulearning.spi.system.entity.TeacherEntity}
 * teacher vo
 *
 * @author luyuhao
 * @since 20/01/17 01:47
 */
@Data
@ApiModel("教师Vo对象")
public class TeacherVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 工号
     */
    @ApiModelProperty("教师工号")
    private String teaNumber;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String teaName;
}
