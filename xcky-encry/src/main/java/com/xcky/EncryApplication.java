package com.xcky;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 程序入口类
 *
 * @author lbchen
 */
@EnableSwagger2
@EnableKnife4j
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
public class EncryApplication {
    public static void main(String[] args) {
        SpringApplication.run(EncryApplication.class, args);
    }
}
