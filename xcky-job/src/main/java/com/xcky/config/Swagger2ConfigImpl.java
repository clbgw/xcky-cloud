package com.xcky.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

/**
 * 文档配置实现类
 * @author lbchen
 */
@Configuration
public class Swagger2ConfigImpl extends AbstractSwagger2Config {
    @Override
    ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("CLOUD 调度服务")
                    .description("调度服务管理")
                    .version("1")
                    .contact(new Contact("lb-chen", "http://localhost:8099", "clb@clb.vip"))
                    .build();
    }
}
