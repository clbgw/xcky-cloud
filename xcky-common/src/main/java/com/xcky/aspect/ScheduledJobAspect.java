package com.xcky.aspect;

import com.alibaba.fastjson.JSONObject;
import com.xcky.annotation.ScheduleJobAnnotation;
import com.xcky.mapper.CronMapper;
import com.xcky.model.entity.CronEntity;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 任务调度工作切面
 *
 * @author lbchen
 */
@Aspect
@Component
@Slf4j
public class ScheduledJobAspect {
    @Autowired
    private CronMapper cronMapper;

    @Pointcut("execution(* com.xcky.schedule.**.*(..))")
    public void scheduledJob() {
    }

    @Around(value = "scheduledJob()")
    public Object doScheduleJob(ProceedingJoinPoint pjp) {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        try {
            Class<?> targetClass = Class.forName(className);
            Method method = targetClass.getMethod(methodName);

            if (method.isAnnotationPresent(ScheduleJobAnnotation.class)) {
                Map<String, Object> map = new HashMap<>();
                map.put("methodName", methodName);
                map.put("className", className);
                List<CronEntity> cronEntities = cronMapper.selectCronEntityByMap(map);
                if (null == cronEntities || cronEntities.size() < 1) {
                    return pjp.proceed();
                } else {
                    String isRun = cronEntities.get(0).getIsRun();
                    if ("1".equals(isRun)) {
                        return "系统正在运行中";
                    }
                }
                // 前置处理逻辑(将任务标记为执行中)
                cronMapper.updateCronStatus(className, methodName, "1", null);
                Object result = pjp.proceed();
                // 后置处理逻辑(将任务标记为处理结束)
                cronMapper.updateCronStatus(className, methodName, "0", null);
                return result;
            }
            return pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            // 异常处理逻辑(将任务标记出具体异常)
            cronMapper.updateCronStatus(className, methodName, "0", JSONObject.toJSONString(e));
            return null;
        }
    }
}


