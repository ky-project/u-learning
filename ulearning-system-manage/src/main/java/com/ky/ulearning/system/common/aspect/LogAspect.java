package com.ky.ulearning.system.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.AopUtil;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import com.ky.ulearning.system.remoting.MonitorManageRemoting;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 日志aop切面类
 *
 * @author luyuhao
 * @since 19/12/05 02:26
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private MonitorManageRemoting monitorManageRemoting;

    private long currentTime = 0L;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.ky.ulearning.common.core.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime = System.currentTimeMillis();
        //执行方法
        result = joinPoint.proceed();
        //设置log属性
        LogEntity logEntity = AopUtil.buildLogEntity(joinPoint, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class),
                RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP), currentTime,
                MicroConstant.LOG_TYPE[0], null, "后台");

        //若ip和username都为null，默许为内部调用，不记录操作表
        if (StringUtil.isEmpty(logEntity.getLogUsername())
                && StringUtil.isEmpty(logEntity.getLogIp())) {
            return result;
        }
        //添加日志
        add(logEntity);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param join join point for advice
     * @param e    exceptions
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint join, Throwable e) {
        ProceedingJoinPoint joinPoint = (ProceedingJoinPoint) join;
        //设置log属性
        LogEntity logEntity = AopUtil.buildLogEntity(joinPoint, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class),
                RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP), currentTime, MicroConstant.LOG_TYPE[1], e.getMessage(), "后台");

        //若ip和username都为null，默许为内部调用，不记录操作表
        if (StringUtil.isEmpty(logEntity.getLogUsername())
                && StringUtil.isEmpty(logEntity.getLogIp())) {
            return;
        }
        //添加日志
        add(logEntity);
    }

    /**
     * 添加日志
     */
    private void add(LogEntity logEntity) {
        Map<String, Object> logMap =
                JSONObject.parseObject(JsonUtil.toJsonString(logEntity));
        try {
            //保存log信息
            monitorManageRemoting.add(logMap);
        } catch (Exception te) {
            log.error("监控系统未启动");
        }
    }
}

