package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.entity.ActivityEntity;
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
     * @param username          用户名
     */
    @Async
    void examinationTaskActivity(Long examinationTaskId, int operation, String username);

    /**
     * 创建实验
     *
     * @param experimentId 实验id
     * @param username     用户名
     */
    @Async
    void experimentActivity(Long experimentId, String username);

    /**
     * 分页查询学生动态
     *
     * @param pageParam 分页参数
     * @param teaId     教师id
     * @return 封装学生动态的分页对象
     */
    PageBean<ActivityEntity> pageList(PageParam pageParam, Long teaId);
}
