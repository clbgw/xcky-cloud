package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserInfo {
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 公众号APPID
     */
    private String appId;
    /**
     * 微信openid
     */
    private String openid;
    /**
     * 微信unionId
     */
    private String unionId;
    /**
     * 会话密钥
     */
    private String sessionKey;
    /**
     * 微信头像
     */
    private String avatarUrl;
    /**
     * 微信用户国家
     */
    private String country;
    /**
     * 微信用户省份
     */
    private String province;
    /**
     * 微信用户城市
     */
    private String city;
    /**
     * 微信用户性别
     */
    private String gender;
    /**
     * 微信用户语言
     */
    private String language;
    /**
     * 微信用户昵称
     */
    private String nickName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
}
