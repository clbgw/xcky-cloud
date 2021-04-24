package com.xcky.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
public class OssConfig {
    /**
     * OSS 的endpoint
     */
    private String endpoint;
    /**
     * 密钥ID
     */
    private String accessKeyId;
    /**
     * 密钥明文
     */
    private String accessKeySecret;
    /**
     * 桶名称
     */
    private String bucketName;
}
