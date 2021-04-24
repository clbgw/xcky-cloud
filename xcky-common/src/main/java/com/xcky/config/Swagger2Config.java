package com.xcky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger2API文档的配置
 *
 * @author lb-chen
 * @creation 2019年6月23日
 */
@Configuration
abstract class AbstractSwagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xcky"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api信息
     *
     * @return
     */
    abstract ApiInfo apiInfo();
}
