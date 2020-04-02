package com.ky.ulearning.student.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @author luyuhao
 * @since 2020/04/03 00:20
 */
public interface ActivityService {


    /**
     * 完成测试动态
     *
     * @param examiningId 学生测试id
     * @param username 用户名
     */
    @Async
    void completeExaminationActivity(Long examiningId, String username);

    /**
     * 完成实验动态
     *
     * @param experimentResultId 学生实验id
     * @param username 用户名
     */
    @Async
    void completeExperimentActivity(Long experimentResultId, String username);
}
