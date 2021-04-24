package com.xcky.schedule;

import com.alibaba.fastjson.JSONObject;
import com.xcky.mapper.CronMapper;
import com.xcky.model.entity.CronEntity;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * 任务调度工作类
 *
 * @author lbchen
 */
@Component
@Slf4j
public class TaskSchedulerJob {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private CronMapper cronMapper;
    /**
     * 缓存已经加入到队列中的cron
     */
    private Set<Long> cronIdCache = new HashSet<>();
    private Map<Long, ScheduledFuture<?>> futureMap = new HashMap<>();

    @Scheduled(cron = "0/10 0 * * * ?")
    public void getData() {
        // 新建列表
        List<CronEntity> listNew = new ArrayList<>();
        // 停止列表
        List<CronEntity> listStop = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(2);
        map.put("isRun", "0");
        List<CronEntity> list = cronMapper.selectCronEntityByMap(map);
        for (CronEntity entity : list) {
            if (!cronIdCache.contains(entity.getId()) && "0".equals(entity.getStatus())) {
                cronIdCache.add(entity.getId());
                listNew.add(entity);
            }
            if (cronIdCache.contains(entity.getId()) && "1".equals(entity.getStatus())) {
                listStop.add(entity);
            }
        }
        start(listNew);
        stop(listStop);
    }

    private void start(List<CronEntity> listNew) {
        for (CronEntity entity : listNew) {
            Long id = entity.getId();
            if (futureMap.containsKey(id)) {
                continue;
            }
            log.error("调度开始: " + JSONObject.toJSONString(entity));
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(
                    () -> {
                        String className = entity.getClassName();
                        String methodName = entity.getMethodName();
                        try {
                            Class<?> clazz = Class.forName(className);
                            Object obj = clazz.getDeclaredConstructor().newInstance();
                            Method method = clazz.getMethod(methodName);
                            method.invoke(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    new CronTrigger(entity.getCron()));
            futureMap.put(id, future);
        }
    }

    private void stop(List<CronEntity> listStop) {
        for (CronEntity entity : listStop) {
            Long id = entity.getId();
            if (futureMap.containsKey(id)) {
                ScheduledFuture<?> future = futureMap.get(id);
                if (future != null) {
                    future.cancel(true);
                }
                futureMap.remove(id);
                cronIdCache.remove(id);
                log.error("调度结束: " + JSONObject.toJSONString(entity));
            }
        }
    }
}
