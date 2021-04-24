package com.xcky.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 线程池配置类
 *
 * @author lbchen
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 创建任务调度器
     *
     * @return 任务调度器
     */
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    /**
     * 接收日志的异步线程池
     *
     * @return 线程池
     */
    @Bean("logExecutorService")
    public ExecutorService logExecutorService() {
        return new ThreadPoolExecutor(
                0,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    /**
     * 发送邮件的异步线程池
     *
     * @return 线程池
     */
    @Bean("emailExecutorService")
    public ExecutorService emailExecutorService() {
        ExecutorService executorService = new ThreadPoolExecutor(
                0,
                10,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
        return executorService;
    }
}
