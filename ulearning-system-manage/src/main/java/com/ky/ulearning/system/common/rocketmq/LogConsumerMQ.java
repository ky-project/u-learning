package com.ky.ulearning.system.common.rocketmq;

import com.ky.ulearning.common.core.rocketmq.AbstractRocketConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志消费者
 *
 * @author luyuhao
 * @since 2020/3/25 20:56
 */
@Component
public class LogConsumerMQ extends AbstractRocketConsumer {

    @Override
    public void init() {
        // 设置主题,标签与消费者标题
        super.necessary("TopicTest", "*", "这是标题");
        //消费者具体执行逻辑
        registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                msgs.forEach(msg -> {
                    System.out.printf("consumer message boyd %s %n", new String(msg.getBody()));
                });
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
