package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 微信用户基本信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserUpdateBaseSubInfoReq {
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
    
    
    /********** 附加属性 ******************/
    /**
     * 用户微信openid
     */
    private String openid;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 最后一次更新时间
     */
    private Date updateTime;
}
