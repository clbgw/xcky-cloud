package com.xcky.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任务调度切面处理注解
 *
 * @author lbchen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduleJobAnnotation {

    /**
     * 描述
     *
     * @return 切面描述
     */
    String value() default "任务调度切面处理";
}
