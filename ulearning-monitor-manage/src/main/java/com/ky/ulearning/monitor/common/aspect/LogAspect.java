package com.ky.ulearning.monitor.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ky.ulearning.common.core.component.component.SpringBeanWrapper;
import com.ky.ulearning.common.core.component.config.properties.RocketMQProperties;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.AopUtil;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.monitor.common.constants.MonitorManageConfigParameters;
import com.ky.ulearning.monitor.service.LogService;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
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
    private LogService logService;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private MonitorManageConfigParameters monitorManageConfigParameters;

    @Autowired
    private RocketMQProperties rocketMQProperties;

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

        //判断是否是开发模式记录的日志
        if (!defaultConfigParameters.isDevMode() && AopUtil.devModel(joinPoint)) {
            return result;
        }

        //设置log属性
        LogEntity logEntity = AopUtil.buildLogEntity(joinPoint, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class),
                RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP), currentTime,
                MicroConstant.LOG_TYPE[0], null, "监控系统");

        //若ip和username都为null，默许为内部调用，不记录操作表
        if (StringUtil.isEmpty(logEntity.getLogUsername())
                && StringUtil.isEmpty(logEntity.getLogIp())) {
            return result;
        }

        //若是分页查询，只记录查询第一页的日志，其余查询不记录
        if (logEntity.getLogParams().contains(AopUtil.PAGE_PARAM)) {
            if (!logEntity.getLogParams().contains(AopUtil.FIRST_PAGE_PARAM)) {
                return result;
            }
        }

        //保存log信息
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
                RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP), currentTime, MicroConstant.LOG_TYPE[1], e.getMessage(), "监控系统");

        //若ip和username都为null，默许为内部调用，不记录操作表
        if (StringUtil.isEmpty(logEntity.getLogUsername())
                && StringUtil.isEmpty(logEntity.getLogIp())) {
            return;
        }

        //保存log信息
        add(logEntity);
    }

    /**
     * 添加日志 - 测试阶段
     */
    private void add(LogEntity logEntity) {
        //添加日志
        if (rocketMQProperties.getIsEnable()) {
            try {
                DefaultMQProducer defaultMQProducer = SpringBeanWrapper.getBean(DefaultMQProducer.class);
                defaultMQProducer.sendOneway(new Message(CommonConstant.ROCKET_LOG_MONITOR_TOPIC, monitorManageConfigParameters.getAppName(), JSON.toJSONString(logEntity).getBytes()));
                log.info(monitorManageConfigParameters.getAppName() + " 生成一条日志发送至队列");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                remotingAdd(logEntity);
            }
        } else {
            log.info(monitorManageConfigParameters.getAppName() + " 生成一条日志发送至队列");
            remotingAdd(logEntity);
        }
    }

    /**
     * service添加日志
     */
    private void remotingAdd(LogEntity logEntity) {
        //保存log信息
        logService.insert(logEntity);
    }
}

