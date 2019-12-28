package com.ky.ulearning.gateway.common.redis.impl;

import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.ky.ulearning.gateway.common.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis操作service-实现类
 *
 * @author luyuhao
 * @date 19/12/07 02:02
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void saveCode(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
        redisTemplate.expire(key, GatewayConstant.LOGIN_CODE_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public String getCodeVal(String key) {
        String value = "";
        Object valueTmp = redisTemplate.opsForValue().get(key);
        if(valueTmp != null){
            value = valueTmp.toString();
        }
        return value;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
