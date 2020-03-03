package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;

import java.util.List;
import java.util.Set;

/**
 * 学生选课service - 接口
 *
 * @author luyuhao
 * @since 20/02/22 14:41
 */
public interface StudentTeachingTaskService {

    /**
     * 根据教学任务id和学生id查询选课信息
     *
     * @param teachingTaskId 教学任务id
     * @param stuId          学生id
     * @return 学生选课信息
     */
    StudentTeachingTaskEntity getByTeachingIdAndStuId(Long teachingTaskId, Long stuId);

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
    void deleteByTeachingTaskIdAndStuId(Long teachingTaskId, Long stuId, String updateBy);

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
     * 查询所有教学任务信息
     *
     * @return 教学任务id-教学任务名
     */
    List<KeyLabelVo> getAllTeachingTaskArray();

    /**
     * 根据学生id查询学生所选的所有教学任务对应的课程id
     *
     * @param stuId 学生id
     * @return 课程id集合
     */
    Set<Long> getCourseIdSetByStuId(Long stuId);
}
