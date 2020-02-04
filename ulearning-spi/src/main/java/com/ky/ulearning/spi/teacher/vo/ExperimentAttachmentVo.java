package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity}
 * 实验附件vo
 *
 * @author luyuhao
 * @since 20/02/04 23:34
 */
@Data
@ApiModel("实验附件vo")
@AllArgsConstructor
@NoArgsConstructor
public class ExperimentAttachmentVo implements Serializable {

    /**
     * 附件URL
     */
    @ApiModelProperty("附件URL")
    private String experimentAttachment;

    /**
     * 附件名
     */
    @ApiModelProperty("附件名")
    private String experimentAttachmentName;
}
