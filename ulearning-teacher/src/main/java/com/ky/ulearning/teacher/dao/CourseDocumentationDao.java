package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件资料dao
 *
 * @author luyuhao
 * @since 2020/02/14 20:23
 */
@Mapper
@Repository
public interface CourseDocumentationDao {

    /**
     * 插入文件资料记录
     *
     * @param courseDocumentationDto 待插入的文件资料对象
     */
    void insert(CourseDocumentationDto courseDocumentationDto);

    /**
     * 根据id查询文件资料
     *
     * @param id 文件资料id
     * @return 文件资料实体类
     */
    CourseFileDocumentationDto getById(Long id);

    /**
     * 更新文件资料
     *
     * @param courseDocumentationDto 待更新的文件资料
     */
    void update(CourseDocumentationDto courseDocumentationDto);

    /**
     * 查询文件资料集合
     *
     * @param courseFileDocumentationDto 筛选对象
     * @return 返回课程文件资料集合
     */
    List<CourseFileDocumentationDto> getList(CourseFileDocumentationDto courseFileDocumentationDto);

    /**
     * 根据文件id查询课程文件资料对象
     *
     * @param fileId 文件id
     * @return 课程文件资料对象
     */
    CourseFileDocumentationDto getByFileId(Long fileId);

    /**
     * 根据id更新valid值
     *
     * @param id       id
     * @param updateBy 更新者
     * @param valid    有效位的值
     */
    void updateValidById(@Param("id") Long id, @Param("updateBy") String updateBy, @Param("valid") Integer valid);

    /**
     * 根据父节点id查询所有课程文件资料
     *
     * @param fileParentId 父节点id
     * @return 课程文件资料
     */
    List<CourseFileDocumentationDto> getListByFileParentId(Long fileParentId);

    /**
     * 根据父节点id和文件类型查询所有课程文件资料
     *
     * @param fileParentId 父节点id
     * @param fileType     文件类型
     * @return 课程文件资料
     */
    List<CourseFileDocumentationDto> getListByFileParentIdAndFileType(@Param("fileParentId") Long fileParentId, @Param("fileType") Integer fileType);

    /**
     * 根据id集合更新分享值
     *
     * @param idList              id集合
     * @param documentationShared 是否共享
     * @param updateBy            更新者
     */
    void updateSharedByIds(@Param("idList") List<Long> idList,
                           @Param("documentationShared") Boolean documentationShared,
                           @Param("updateBy") String updateBy);

    /**
     * 根据id更新分享值
     *
     * @param id                  id
     * @param documentationShared 是否共享
     * @param updateBy            更新者
     */
    void updateSharedById(@Param("id") Long id,
                          @Param("documentationShared") Boolean documentationShared,
                          @Param("updateBy") String updateBy);

    /**
     * 查询共享区文件资料列表
     *
     * @param courseFileDocumentationDto 查询条件
     * @return 文件资料列表
     */
    List<CourseFileDocumentationDto> getSharedList(CourseFileDocumentationDto courseFileDocumentationDto);
}
