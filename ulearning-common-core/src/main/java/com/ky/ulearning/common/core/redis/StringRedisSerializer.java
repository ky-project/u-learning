package com.ky.ulearning.common.core.redis;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.Charset;

/**
 * 重写序列化器
 *
 * @author luyuhao
 * @date 19/12/06 20:22
 */
public class StringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public StringRedisSerializer() {
        this(Charset.forName("UTF8"));
    }

    public StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object object) {
        String target = "\"";
        String replacement = "";
        String string = JSON.toJSONString(object);
        string = string.replace(target, replacement);
        return string.getBytes(charset);
    }
}
