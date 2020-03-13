package com.ky.ulearning.common.core.component.aspect;

import com.ky.ulearning.common.core.component.component.RedisClientWrapper;
import com.ky.ulearning.common.core.constant.MicroConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 清空登录用户缓存aop
 *
 * @author luyuhao
 * @since 20/03/04 23:12
 */
@Aspect
@Component
@Slf4j
public class DeleteUserRedisAspect {

    @Autowired
    private RedisClientWrapper redisClientWrapper;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.ky.ulearning.common.core.annotation.DeleteUserRedis)")
    public void deleteUserRedisPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    @After("deleteUserRedisPointcut()")
    public void deleteUserRedis(){
        //清除登录用户缓存
        redisClientWrapper.batchDeleteByPrefix(MicroConstant.LOGIN_USER_REDIS_PREFIX);
    }
}
