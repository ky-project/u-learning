package com.ky.ulearning.common.core.component.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @since 2020/3/26 13:21
 */
@Component
public class SpringBeanWrapper implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private ApplicationContext applicationContext;

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return 返回实例
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name 实例名
     * @return 实例对象
     */
    public Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz class
     * @return 返回实例
     */
    public <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name  实例名
     * @param clazz 实例类型
     * @return 实例对象
     */
    public <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
