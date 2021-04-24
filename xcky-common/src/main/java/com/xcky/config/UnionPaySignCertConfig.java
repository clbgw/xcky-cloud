package com.xcky.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 银联支付签名证书配置
 *
 * @author lbchen
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "signcert")
public class UnionPaySignCertConfig {
    private String pwd;
    private String type;
    private String path;
}
