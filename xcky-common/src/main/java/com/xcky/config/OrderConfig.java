package com.xcky.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * 订单配置类
 *
 * @author lbchen
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "order")
public class OrderConfig {
    /**
     * 1积分可兑换的金额(元)
     */
    BigDecimal integralMulti;
}
