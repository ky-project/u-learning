package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 学生选课dao
 *
 * @author luyuhao
 * @since 20/02/22 14:40
 */
@Mapper
@Repository
public interface StudentTeachingTaskDao {

    /**
     * 根据教学任务id和学生id查询选课信息
     *
     * @param teachingTaskId 教学任务id
     * @param stuId          学生id
     * @return 学生选课信息
     */
    StudentTeachingTaskEntity getByTeachingIdAndStuId(@Param("teachingTaskId") Long teachingTaskId, @Param("stuId") Long stuId);

    /**
     * 新增选课信息
     *
     * @param studentTeachingTaskEntity 学生选课对象
     */
    void insert(StudentTeachingTaskEntity studentTeachingTaskEntity);

    /**
     * 删除选课信息
     *
     * @param teachingTaskId 教学任务id
     * @param stuId          学生id
     * @param updateBy       更新者
     */
    void deleteByTeachingTaskIdAndStuId(@Param("teachingTaskId") Long teachingTaskId,
                                        @Param("stuId") Long stuId,
                                        @Param("updateBy") String updateBy);

    /**
     * 获取教学任务数组
     *
     * @param stuId 学生id
     * @return 教学任务数组
     */
    List<KeyLabelVo> getTeachingTaskArray(Long stuId);

    /**
     * 根据stuId查询对应的教学任务id集合
     *
     * @param stuId 学生id
     * @return 教学任务id集合
     */
    Set<Long> getTeachingTaskIdSetByStuId(Long stuId);

    /**
     * 根据学生id查询学生所选的所有教学任务对应的课程id
     *
     * @param stuId 学生id
     * @return 课程id集合
     */
    Set<Long> getCourseIdSetByStuId(Long stuId);
}
