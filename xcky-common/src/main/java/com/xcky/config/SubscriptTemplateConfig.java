package com.xcky.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author lbchen
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sub")
public class SubscriptTemplateConfig {
    /**
     * 签到统计提醒<br>
     * 在线教育-签到提醒
     */
    private Map<String, Object> checkinSub;

    /**
     * 签到成功提醒<br>
     * 信息查询-签到成功提醒
     */
    private Map<String, Object> checkinOk;
}
