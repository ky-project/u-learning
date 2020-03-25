package com.ky.ulearning.common.core.rocketmq;

import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;

/**
 * 消费者接口
 *
 * @author luyuhao
 * @since 2020/3/25 20:36
 */
public interface RocketConsumer {

    /**
     * 初始化消费者
     */
    void init();

    /**
     * 注册监听
     *
     * @param messageListener rocketMQ 监听器
     */
    void registerMessageListener(MessageListenerConcurrently messageListener);
}
