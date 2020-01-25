package com.ky.ulearning.common.core.utils;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author luyuhao
 * @since 20/01/25 22:18
 */
public class AopUtil {

    /**
     * 创建日志对象
     *
     * @param joinPoint    切点
     * @param username     用户名
     * @param ip           ip
     * @param currentTime  开始执行时间
     * @param logType      日志类型
     * @param exceptionMsg 错误信息
     * @return 日志对象
     */
    public static LogEntity buildLogEntity(ProceedingJoinPoint joinPoint, String username, String ip, long currentTime, String logType, String exceptionMsg) {
        LogEntity logEntity = new LogEntity();
        //获取用户信息
        logEntity.setLogUsername(username);
        logEntity.setLogDescription(getDescription(joinPoint));
        logEntity.setLogModule(getModule(joinPoint));
        logEntity.setLogIp(ip);
        logEntity.setLogType(logType);
        logEntity.setLogException(exceptionMsg);
        logEntity.setLogParams(getParams(joinPoint));
        logEntity.setLogTime(System.currentTimeMillis() - currentTime);
        logEntity.setLogAddress(IpUtil.getCityInfo(logEntity.getLogIp()));
        logEntity.setCreateBy("system");
        logEntity.setUpdateBy("system");
        return logEntity;
    }

    /**
     * 获取参数
     */
    private static String getParams(ProceedingJoinPoint joinPoint) {
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
    private static String getModule(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
    }

    /**
     * 获取注解中的用户操作信息
     */
    private static String getDescription(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);
        // 描述
        return aopLog.value();
    }
}