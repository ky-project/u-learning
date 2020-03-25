package com.ky.ulearning.common.core.component.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * rocketMQ生产者配置
 *
 * @author luyuhao
 * @since 2020/3/25 17:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties {

    /**
     * 是否开启rocketMQ
     */
    private Boolean isEnable;

    /**
     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     */
    private String groupName;

    /**
     * mq的nameserver地址
     */
    private String namesrvAddr;

    /**
     * vip通道，是否使用10909端口
     */
    private Boolean vipChannelEnabled;

    /**
     * 消息最大长度 默认1024*4(4M)
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;

    /**
     * 发送消息超时时间,默认3000
     */
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;

    /**
     * 发送消息失败重试次数，默认2
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private Integer consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private Integer consumeThreadMax;

    /**
     * 设置一次消费消息的条数，默认为1条
     */
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private Integer consumeMessageBatchMaxSize;

}
