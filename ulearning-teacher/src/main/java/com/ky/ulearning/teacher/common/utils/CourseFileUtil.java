package com.ky.ulearning.teacher.common.utils;

import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.FileUtil;
import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 课程文件相关工具类
 *
 * @author luyuhao
 * @since 20/02/17 20:25
 */
public class CourseFileUtil {

    private static final String FOLDER_NAME = "文件夹";

    /**
     * 根据参数创建课程文件对象
     */
    public static CourseFileDto createCourseFileDto(Long courseId, String fileUrl, MultipartFile file, Long fileParentId, String username) {
        CourseFileDto courseFileDto = new CourseFileDto();
        courseFileDto.setCourseId(courseId);
        courseFileDto.setFileUrl(fileUrl);
        courseFileDto.setFileName(FileUtil.getFileNameNoEx(file.getOriginalFilename()));
        courseFileDto.setFileSize(file.getSize());
        courseFileDto.setFileExt(FilenameUtils.getExtension(file.getOriginalFilename()));
        courseFileDto.setFileType(MicroConstant.FILE_TYPE);
        courseFileDto.setFileParentId(fileParentId);
        courseFileDto.setUpdateBy(username);
        courseFileDto.setCreateBy(username);
        return courseFileDto;
    }

    /**
     * 创建文件资料文件夹对象
     */
    public static CourseDocumentationDto createCourseDocumentationDtoFolder(String username) {
        CourseDocumentationDto courseDocumentationDto = new CourseDocumentationDto();
        courseDocumentationDto.setDocumentationTitle(FOLDER_NAME);
        courseDocumentationDto.setDocumentationSummary(FOLDER_NAME);
        courseDocumentationDto.setDocumentationCategory((short) 0);
        courseDocumentationDto.setDocumentationShared(false);
        courseDocumentationDto.setUpdateBy(username);
        courseDocumentationDto.setCreateBy(username);
        return courseDocumentationDto;
    }

    /**
     * 创建教学资源文件夹对象
     */
    public static CourseResourceDto createCourseResourceDtoFolder(String username) {
        CourseResourceDto courseResourceDto = new CourseResourceDto();
        courseResourceDto.setResourceTitle(FOLDER_NAME);
        courseResourceDto.setResourceSummary(FOLDER_NAME);
        courseResourceDto.setResourceType((short) 0);
        courseResourceDto.setResourceShared(false);
        courseResourceDto.setUpdateBy(username);
        courseResourceDto.setCreateBy(username);
        return courseResourceDto;
    }
}
