package com.ky.ulearning.student.common.utils;

import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;
import com.ky.ulearning.student.service.StudentTeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 学生选课相关工具类
 *
 * @author luyuhao
 * @since 20/02/22 14:38
 */
@Component
public class StudentTeachingTaskUtil {

    @Autowired
    private StudentTeachingTaskService studentTeachingTaskService;

    /**
     * 验证学生是否已经选修该课程
     */
    public boolean selectedTeachingTask(Long teachingTaskId, Long stuId) {
        StudentTeachingTaskEntity studentTeachingTaskEntity = studentTeachingTaskService.getByTeachingIdAndStuId(teachingTaskId, stuId);
        return studentTeachingTaskEntity != null;
    }
}
