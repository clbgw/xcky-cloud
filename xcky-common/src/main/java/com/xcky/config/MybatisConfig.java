package com.xcky.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 配置类
 *
 * @author lbchen
 */
@Configuration
@MapperScan(basePackages = {"com.xcky.mapper"})
public class MybatisConfig {
}
