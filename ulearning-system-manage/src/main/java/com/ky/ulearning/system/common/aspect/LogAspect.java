package com.ky.ulearning.system.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.IpUtil;
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
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 日志aop切面类
 *
 * @author luyuhao
 * @date 19/12/05 02:26
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
        LogEntity logEntity = new LogEntity();
        //获取用户信息
        logEntity.setLogUsername(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        logEntity.setLogDescription(getDescription(joinPoint));
        logEntity.setLogModule(getModule(joinPoint));
        logEntity.setLogIp(RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP));
        logEntity.setLogType("INFO");
        logEntity.setLogParams(getParams(joinPoint));
        logEntity.setLogTime(System.currentTimeMillis() - currentTime);
        logEntity.setLogAddress(IpUtil.getCityInfo(logEntity.getLogIp()));
        logEntity.setCreateBy("system");
        logEntity.setUpdateBy("system");

        //若ip和username都为null，默许为内部调用，不记录操作表
        if (StringUtil.isEmpty(logEntity.getLogUsername())
                && StringUtil.isEmpty(logEntity.getLogIp())) {
            return result;
        }

        Map<String, Object> logMap =
                JSONObject.parseObject(JsonUtil.toJsonString(logEntity));
        try {
            //保存log信息
            monitorManageRemoting.add(logMap);
        } catch (Exception e) {
            log.error("监控系统未启动");
        }
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
        LogEntity logEntity = new LogEntity();
        //获取用户信息
        logEntity.setLogUsername(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        logEntity.setLogDescription(getDescription(joinPoint));
        logEntity.setLogModule(getModule(joinPoint));
        logEntity.setLogIp(RequestHolderUtil.getHeaderByName(MicroConstant.USER_REQUEST_IP));
        logEntity.setLogType("ERROR");
        logEntity.setLogException(e.getMessage());
        logEntity.setLogParams(getParams(joinPoint));
        logEntity.setLogTime(System.currentTimeMillis() - currentTime);
        logEntity.setLogAddress(IpUtil.getCityInfo(logEntity.getLogIp()));
        logEntity.setCreateBy("system");
        logEntity.setUpdateBy("system");

        //若ip和username都为null，默许为内部调用，不记录操作表
        if (StringUtil.isEmpty(logEntity.getLogUsername())
                && StringUtil.isEmpty(logEntity.getLogIp())) {
            return;
        }

        Map<String, Object> logMap =
                JSONObject.parseObject(JsonUtil.toJsonString(logEntity));
        try {
            //保存log信息
            monitorManageRemoting.add(logMap);
        } catch (Exception te) {
            log.error("监控系统未启动");
        }
    }

    /**
     * 获取参数
     */
    private String getParams(ProceedingJoinPoint joinPoint) {
        String params = "{";
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                if (argNames[i].contains("request")
                        || argNames[i].contains("response")) {
                    continue;
                }
                params += " " + argNames[i] + ": " + argValues[i];
            }
        }
        params += "}";
        if (params.length() >= 1023) {
            params = params.substring(0, 1023);
        }
        return params;
    }

    /**
     * 获取方法名
     */
    private String getModule(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
    }

    /**
     * 获取注解中的用户操作信息
     */
    private String getDescription(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);
        // 描述
        return aopLog.value();
    }

}

