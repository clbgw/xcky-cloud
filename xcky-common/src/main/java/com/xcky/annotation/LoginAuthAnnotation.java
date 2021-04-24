package com.xcky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录权限校验
 *
 * @author lbchen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAuthAnnotation {

    /**
     * 描述
     *
     * @return 登录描述
     */
    String value() default "请求头必须带有有效的token";
}
