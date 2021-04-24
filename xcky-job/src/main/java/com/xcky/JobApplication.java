package com.xcky;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 任务应用启动类
 *
 * @author lbchen
 */
@EnableSwagger2
@EnableKnife4j
@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableFeignClients
@EnableDiscoveryClient
public class JobApplication {
    /**
     * 程序启动入口
     *
     * @param args 启动入参
     */
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class,args);
    }
}
