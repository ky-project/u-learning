package com.ky.ulearning.common.core.component.config;

import com.ky.ulearning.common.core.component.config.properties.RocketMQProperties;
import com.ky.ulearning.common.core.rocketmq.AbstractRocketConsumer;
import com.ky.ulearning.common.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author luyuhao
 * @since 2020/3/25 20:40
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({RocketMQProperties.class})
@ConditionalOnProperty(prefix = "rocketmq", value = "isEnable", havingValue = "true")
public class RocketMQConfig {

    private RocketMQProperties rocketMQProperties;

    private ApplicationContext applicationContext;


    public RocketMQConfig(RocketMQProperties rocketMQProperties, ApplicationContext applicationContext) {
        this.rocketMQProperties = rocketMQProperties;
        this.applicationContext = applicationContext;
    }

    /**
     * 注入一个默认的生产者
     */
    @Bean(destroyMethod = "shutdown")
    public DefaultMQProducer getRocketMQProducer() throws MQClientException {
        if (StringUtil.isEmpty(rocketMQProperties.getGroupName())) {
            throw new MQClientException(-1, "groupName is blank");
        }

        if (StringUtil.isEmpty(rocketMQProperties.getNamesrvAddr())) {
            throw new MQClientException(-1, "nameServerAddr is blank");
        }
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(rocketMQProperties.getGroupName());

        producer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        // producer.setCreateTopicKey("AUTO_CREATE_TOPIC_KEY");

        // 如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        // producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(rocketMQProperties.getMaxMessageSize());
        producer.setSendMsgTimeout(rocketMQProperties.getSendMsgTimeout());
        // 如果发送消息失败，设置重试次数，默认为2次
        producer.setRetryTimesWhenSendFailed(rocketMQProperties.getRetryTimesWhenSendFailed());
        //配置是否使用vip通道
        producer.setVipChannelEnabled(rocketMQProperties.getVipChannelEnabled());

        try {
            producer.start();
            log.info("producer is start ! groupName:{},namesrvAddr:{}", rocketMQProperties.getGroupName(),
                    rocketMQProperties.getNamesrvAddr());
        } catch (MQClientException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return producer;
    }

    /**
     * SpringBoot启动时加载所有消费者
     */
    @PostConstruct
    public void initConsumer() {
        Map<String, AbstractRocketConsumer> consumers = applicationContext.getBeansOfType(AbstractRocketConsumer.class);
        if (CollectionUtils.isEmpty(consumers)) {
            log.info("init rocket consumer 0");
        }
        for (String beanName : consumers.keySet()) {
            AbstractRocketConsumer consumer = consumers.get(beanName);
            consumer.init();
            createConsumer(consumer);
            log.info("init success consumer title {} , toips {} , tags {}", consumer.getConsumerTitle(), consumer.getTags(),
                    consumer.getTopics());
        }
    }

    /**
     * 通过消费者信心创建消费者
     *
     * @param arc 消费者
     */
    private void createConsumer(AbstractRocketConsumer arc) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.rocketMQProperties.getGroupName());
        consumer.setNamesrvAddr(this.rocketMQProperties.getNamesrvAddr());
        consumer.setConsumeThreadMin(this.rocketMQProperties.getConsumeThreadMin());
        consumer.setConsumeThreadMax(this.rocketMQProperties.getConsumeThreadMax());
        consumer.setVipChannelEnabled(this.rocketMQProperties.getVipChannelEnabled());
        consumer.registerMessageListener(arc.getMessageListenerConcurrently());
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费 如果非第一次启动，那么按照上次消费的位置继续消费
        // consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //设置消费模型，集群还是广播，默认为集群
        consumer.setMessageModel(arc.getMessageModel());

        //设置一次消费消息的条数，默认为1条
        consumer.setConsumeMessageBatchMaxSize(arc.getConsumeMessageBatchMaxSize());
        try {
            consumer.subscribe(arc.getTopics(), arc.getTags());
            consumer.start();
            arc.setMqPushConsumer(consumer);
        } catch (MQClientException e) {
            log.error("info consumer title {}", arc.getConsumerTitle(), e);
        }

    }
}
