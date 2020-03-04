package com.ky.ulearning.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示需要删除登录用户缓存的切点
 *
 * @author luyuhao
 * @since 20/03/04 23:10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteUserRedis {

    String value() default "";
}
