package com.xcky.config;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

/**
 * @author lbchen
 */
public class Swagger2ConfigImpl extends AbstractSwagger2Config {
    @Override
    ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("CLOUD 订单服务")
                    .description("订单服务管理")
                    .version("1")
                    .contact(new Contact("lb-chen", "http://localhost:8092", "clb@clb.vip"))
                    .build();
    }
}
