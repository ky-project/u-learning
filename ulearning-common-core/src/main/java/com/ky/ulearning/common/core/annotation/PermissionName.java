package com.ky.ulearning.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luyuhao
 * @date 2019/12/6 16:36
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionName {
    /**
     * 资源名
     */
    String source();

    /**
     * 访问的url
     */
    String url() default "";

    /**
     * 操作显示名
     */
    String name();

    /**
     * 操作组
     */
    String group();
}
