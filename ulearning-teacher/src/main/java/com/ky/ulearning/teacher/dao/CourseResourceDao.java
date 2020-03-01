package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教学资源dao
 *
 * @author luyuhao
 * @since 2020/02/17 19:14
 */
@Mapper
@Repository
public interface CourseResourceDao {

    /**
     * 插入教学资源信息
     *
     * @param courseResourceDto 待插入的教学资源
     */
    void insert(CourseResourceDto courseResourceDto);

    /**
     * 根据id查询教学资源
     *
     * @param id 教学资源id
     * @return 课程文件教学资源对象
     */
    CourseFileResourceDto getById(Long id);

    /**
     * 更新教学资源
     *
     * @param courseResourceDto 待更新的教学资源对象
     */
    void update(CourseResourceDto courseResourceDto);

    /**
     * 根据文件id查询课程文件资料对象
     *
     * @param fileId 文件id
     * @return 课程文件资料对象
     */
    CourseFileResourceDto getByFileId(Long fileId);

    /**
     * 查询教学资源集合
     *
     * @param courseFileResourceDto 筛选对象
     * @return 返回课程文件教学资源集合
     */
    List<CourseFileResourceDto> getList(CourseFileResourceDto courseFileResourceDto);

    /**
     * 根据id更新valid值
     *
     * @param id       教学资源id
     * @param updateBy 更新者
     * @param valid    有效值
     */
    void updateValidById(@Param("id") Long id,
                         @Param("updateBy") String updateBy,
                         @Param("valid") Integer valid);

    /**
     * 根据父节点id查询所有课程文件教学资源
     *
     * @param fileParentId 父节点id
     * @return 课程文件教学资源
     */
    List<CourseFileResourceDto> getListByFileParentId(Long fileParentId);

    /**
     * 根据父节点和文件类型查询课程文件教学资源
     *
     * @param fileParentId 父节点id
     * @param fileType     文件类型
     * @return 课程文件教学资源
     */
    List<CourseFileResourceDto> getListByFileParentIdAndFileType(@Param("fileParentId") Long fileParentId, @Param("fileType") Integer fileType);

    /**
     * 根据id更新分享值
     *
     * @param id             id
     * @param resourceShared 分享值
     * @param updateBy       更新者
     */
    void updateSharedById(@Param("id") Long id,
                          @Param("resourceShared") Boolean resourceShared,
                          @Param("updateBy") String updateBy);

    /**
     * 根据id集合更新分享值
     *
     * @param idList         id集合
     * @param resourceShared 分享值
     * @param updateBy       更新者
     */
    void updateSharedByIds(@Param("idList") List<Long> idList,
                           @Param("resourceShared") Boolean resourceShared,
                           @Param("updateBy") String updateBy);

    /**
     * 查询分享区教学资源列表
     *
     * @param courseFileResourceDto 查询条件
     * @return 教学资源集合
     */
    List<CourseFileResourceDto> getSharedList(CourseFileResourceDto courseFileResourceDto);
}
