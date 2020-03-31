package com.ky.ulearning.monitor.common.rocketmq;

import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.rocketmq.AbstractRocketConsumer;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.monitor.service.ActivityService;
import com.ky.ulearning.spi.monitor.entity.ActivityEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 教师动态消费者
 *
 * @author luyuhao
 * @since 2020/04/01 00:23
 */
@Slf4j
@Component
public class TeacherActivityConsumerMQ extends AbstractRocketConsumer {

    @Autowired
    private ActivityService activityService;

    @Override
    public void init() {
        // 设置主题,标签与消费者标题
        super.necessary(CommonConstant.ROCKET_LOG_TEACHER_ACTIVITY_TOPIC, "*", "教师动态消费者");
        //消费者具体执行逻辑
        registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt msg : msgs) {
                        String json = new String(msg.getBody(), CommonConstant.CHARSET);
                        ActivityEntity activityEntity = JsonUtil.parseObject(json, ActivityEntity.class);
                        activityService.insertTeacherActivity(activityEntity);
                        log.info("接受来自 {} 的教师动态信息并处理，队列偏移 {}, 消息长度 {}", msg.getTags(), msg.getQueueOffset(), msgs.size());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
