package com.xcky.config;

import com.xcky.util.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 全局WEB的配置
 *
 * @author lbchen
 */
@Configuration
public class GlobalWebConfig extends WebMvcConfigurationSupport {
    /**
     * 解决CORS的问题
     *
     * @param registry cors注册对象
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true)
                // 向前端js暴露的请求头
                .exposedHeaders(Constants.ACCESS_TOKEN_HEADER);
    }

    /**
     * 设置默认返回JSON格式
     *
     * @param configurer 配置参数
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /**
         *因为sentinel 的引入，导致返回值格式为xml<br>
         *spring-cloud-starter-alibaba-sentinel<br>
         * -- jackson-dataformat-xml-2.11.2.jar
         */
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}