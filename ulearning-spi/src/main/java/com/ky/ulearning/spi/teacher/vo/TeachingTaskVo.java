package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luyuhao
 * @since 20/02/13 22:37
 */
@Data
@ApiModel("教学任务vo")
@AllArgsConstructor
@NoArgsConstructor
public class TeachingTaskVo {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 教学任务别称
     */
    @ApiModelProperty("教学任务别称")
    private String teachingTaskAlias;

    /**
     * 开课学期
     */
    @ApiModelProperty("开课学期")
    private String term;
}
