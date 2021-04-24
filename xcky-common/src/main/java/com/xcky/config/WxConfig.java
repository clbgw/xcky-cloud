package com.xcky.config;

import com.xcky.model.pojo.MchAccount;
import com.xcky.model.pojo.MiniAccount;
import com.xcky.model.pojo.MiniTtAccount;
import com.xcky.model.pojo.SerAccount;
import com.xcky.model.pojo.SubAccount;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信账号管理
 *
 * @author lbchen
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WxConfig {
    /**
     * 微信小程序账号Map,key为小程序的APPID
     */
    private Map<String, MiniAccount> miniAccount;
    /**
     * 微信商户号Map,key为商户号
     */
    private Map<String, MchAccount> mchAccount;
    /**
     * 微信订阅号Map,key为订阅号的APPID
     */
    private Map<String, SubAccount> subAccount;
    /**
     * 微信订阅号Map,key为订阅号的APPID
     */
    private Map<String, SerAccount> serAccount;
    /**
     * 头条小程序账号Map,key为小程序的APPID
     */
    private Map<String, MiniTtAccount> miniTtAccount;
}
