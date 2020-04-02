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
     * 创建/开始测试任务
     *
     * @param examinationTaskId 测试任务id
     * @param operation         操作类型
     */
    @Async
    void examinationTaskActivity(Long examinationTaskId, int operation);

    /**
     * 创建实验
     *
     * @param experimentId 实验id
     */
    @Async
    void experimentActivity(Long experimentId);
}
