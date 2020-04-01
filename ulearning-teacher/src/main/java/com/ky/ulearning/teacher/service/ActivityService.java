package com.ky.ulearning.teacher.service;

import org.springframework.scheduling.annotation.Async;

/**
 * 动态service - 接口
 *
 * @author luyuhao
 * @since 2020/04/01 00:05
 */
public interface ActivityService {


    /**
     * 创建测试任务
     *
     * @param examinationTaskId 测试任务id
     */
    @Async
    void createExaminationTask(Long examinationTaskId);
}
