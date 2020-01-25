package com.ky.ulearning.common.core.redis;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ky.ulearning.common.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * Value 序列化
 *
 * @author luyuhao
 * @since 19/12/06 20:18
 */
@Slf4j
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {


    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final SerializerFeature[] FEATURES = {
            SerializerFeature.SkipTransientField,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteClassName
    };

    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JsonUtil.toJsonString(t, FEATURES).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JsonUtil.parseObject(str, clazz);
    }
}
