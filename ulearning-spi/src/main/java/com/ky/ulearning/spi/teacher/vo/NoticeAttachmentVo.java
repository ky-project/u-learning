package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通告附件vo
 *
 * @author luyuhao
 * @since 20/02/02 21:14
 */
@Data
@ApiModel("通告附件vo")
@AllArgsConstructor
@NoArgsConstructor
public class NoticeAttachmentVo implements Serializable {

    /**
     * 附件url，逗号分隔
     */
    @ApiModelProperty("附件url，逗号分隔")
    private String noticeAttachment;

    /**
     * 附件名，逗号分隔
     */
    @ApiModelProperty("附件名，逗号分隔")
    private String noticeAttachmentName;
}
