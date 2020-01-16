package com.ky.ulearning.common.core.component.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.ky.ulearning.common.core.redis.FastJsonRedisSerializer;
import com.ky.ulearning.common.core.redis.StringRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * Redis配置类
 * ConditionalOnClass 自动配置
 *
 * @author luyuhao
 * @date 19/12/06 20:24
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 设置 redis 数据默认过期时间，默认1天
     * 设置@cacheable 序列化方式
     *
     * @return RedisCacheConfiguration
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer)).entryTtl(Duration.ofDays(1));
        return configuration;
    }

    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);

        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.common.dto");
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.common.entity");
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.common.vo");
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.monitor.logging.entity");
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.monitor.logging.dto");
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.system.entity");
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.system.dto");
        ParserConfig.getGlobalInstance().addAccept("com.ky.ulearning.spi.system.vo");

        //使用StringRedisSerializer来序列化和反序列化redis的key
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //开启事务
        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 自定义缓存key生成策略
     * 使用方法 @Cacheable(keyGenerator="keyGenerator")
     *
     * @return KeyGenerator
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append("-");
                sb.append(obj);
            }
            return sb.toString();
        };
    }
}