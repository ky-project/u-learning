package com.ky.ulearning.teacher.dao;


import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.teacher.dto.StudentTeachingTaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 学生选课dao
 *
 * @author luyuhao
 * @since 2020/01/30 00:25
 */
@Mapper
@Repository
public interface StudentTeachingTaskDao {
    /**
     * 删除关联信息
     *
     * @param id 关联id
     */
    void delete(Long id);

    /**
     * 插入关联信息
     *
     * @param studentTeachingTaskDto 待更新的关联对象
     */
    void insert(StudentTeachingTaskDto studentTeachingTaskDto);

    /**
     * 分页查询选课学生信息
     *
     * @param studentDto     学生信息筛选对象
     * @param teachingTaskId 教学任务id
     * @param pageParam      分页参数
     * @return 学生信息集合
     */
    List<StudentEntity> listPage(@Param("studentDto") StudentDto studentDto,
                                 @Param("teachingTaskId") Long teachingTaskId,
                                 @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询选课学生信息 - 总记录数
     *
     * @param studentDto     学生信息筛选对象
     * @param teachingTaskId 教学任务id
     * @return 总记录数
     */
    Integer countListPage(@Param("studentDto") StudentDto studentDto, @Param("teachingTaskId") Long teachingTaskId);

    /**
     * 根据教学任务id查询学生id集合
     *
     * @param teachingTaskIdSet 教学任务id集合
     * @return 学生id集合
     */
    Set<Long> getStuIdSetByTeachingTaskId(@Param("teachingTaskIdSet") Set<Long> teachingTaskIdSet);

    /**
     * 移除选课学生
     *
     * @param studentTeachingTaskDto 选课学生信息
     */
    void remove(@Param("studentTeachingTaskDto") StudentTeachingTaskDto studentTeachingTaskDto);

    /**
     * 根据教学任务id统计学生选课人数
     *
     * @param teachingTaskId 教学任务id
     * @return 学生人数
     */
    Integer countByTeachingTaskId(Long teachingTaskId);
}
