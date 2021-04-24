package com.xcky.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信二维码配置
 *
 * @author lbchen
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "wx.mini.qrcode")
public class WxMiniQrcodeConfig {
    /**
     * 微信小程序二维码存放路径
     */
    private String destPath;
    /**
     * 微信小程序二维码访问路径前缀
     */
    private String urlPrefix;
    /**
     * 跳转的微信页
     */
    private String page;

}
