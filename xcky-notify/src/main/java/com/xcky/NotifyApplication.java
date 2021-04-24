package com.xcky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 通知应用启动类
 *
 * @author lbchen
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class NotifyApplication {
    /**
     * 程序启动入口
     *
     * @param args 启动入参
     */
    public static void main(String[] args) {
        SpringApplication.run(NotifyApplication.class, args);
    }
}
