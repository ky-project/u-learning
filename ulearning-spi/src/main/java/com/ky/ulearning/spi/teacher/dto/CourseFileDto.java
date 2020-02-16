package com.ky.ulearning.spi.teacher.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.teacher.entity.CourseFileEntity}
 * 课程文件dto
 *
 * @author luyuhao
 * @since 20/02/14 02:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("课程文件dto")
public class CourseFileDto extends BaseDto {

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", hidden = true)
    private Long courseId;

    /**
     * 文件url
     */
    @ApiModelProperty(value = "文件url", hidden = true)
    private String fileUrl;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小", hidden = true)
    private Long fileSize;

    /**
     * 文件后缀名
     */
    @ApiModelProperty(value = "文件后缀名", hidden = true)
    private String fileExt;

    /**
     * 文件类型 1：文件 2：文件夹
     * 967999、26212345
     */
    @ApiModelProperty(value = "文件类型 1：文件 2：文件夹")
    private Integer fileType;

    /**
     * 文件所属文件夹 0为根目录
     */
    @ApiModelProperty(value = "文件所属文件夹 0为根目录")
    private Long fileParentId;

    /**
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    private Long teachingTaskId;
}
