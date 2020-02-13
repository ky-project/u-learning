package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程文件实体类
 *
 * @author luyuhao
 * @since 2020/02/14 02:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("课程文件实体类")
public class CourseFileEntity extends BaseEntity {

    /**
     * 课程ID
     */
    @ApiModelProperty("课程ID")
    private Long courseId;

    /**
     * 文件url
     */
    @ApiModelProperty("文件url")
    private String fileUrl;

    /**
     * 文件名
     */
    @ApiModelProperty("文件名")
    private String fileName;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Long fileSize;

    /**
     * 文件后缀名
     */
    @ApiModelProperty("文件后缀名")
    private String fileExt;

    /**
     * 文件类型 1：文件 2：文件夹
     */
    @ApiModelProperty("文件类型 1：文件 2：文件夹")
    private Integer fileType;

    /**
     * 文件所属文件夹 0为根目录
     */
    @ApiModelProperty("文件所属文件夹 0为根目录")
    private Long fileParentId;
}