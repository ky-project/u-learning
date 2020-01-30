package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.teacher.dto.StudentTeachingTaskDto;

import java.util.Set;

/**
 * 学生选课service - 接口类
 *
 * @author luyuhao
 * @since 20/01/30 00:30
 */
public interface StudentTeachingTaskService {
    /**
     * 分页查询选课学生信息
     *
     * @param studentDto     学生信息筛选对象
     * @param teachingTaskId 教学任务id
     * @param pageParam      分页参数
     * @return 封装学生信息的分页对象
     */
    PageBean<StudentEntity> pageStudentList(StudentDto studentDto, Long teachingTaskId, PageParam pageParam);

    /**
     * 根据教学任务id查询学生id集合
     *
     * @param teachingTaskIdSet 教学任务id集合
     * @return 学生id集合
     */
    Set<Long> getStuIdSetByTeachingTaskId(Set<Long> teachingTaskIdSet);

    /**
     * 移除选课学生
     *
     * @param studentTeachingTaskDto 选课学生信息
     */
    void remove(StudentTeachingTaskDto studentTeachingTaskDto);
}
