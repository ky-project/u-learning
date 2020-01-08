package com.ky.ulearning.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.lang.reflect.Method;

/**
 * 异步调用异常捕获
 *
 * @author luyuhao
 * @date 20/01/09 02:05
 */
@Slf4j
@Configuration
public class AsyncExceptionConfig implements AsyncConfigurer{

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @SuppressWarnings("NullableProblems")
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
            StringBuilder stringBuilder = new StringBuilder(method.getName() + "异步请求失败 请求参数:{");
            for (Object object : objects) {
                stringBuilder.append(object);
            }
            stringBuilder.append("}");
            log.error(stringBuilder.toString());
        }
    }
}
