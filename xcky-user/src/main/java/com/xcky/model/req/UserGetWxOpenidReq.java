package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取微信小程序openid请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserGetWxOpenidReq {
    /**
     * 微信小程序openid必传的code参数
     */
    private String code;
    /**
     * 小程序应用ID
     */
    private String appid;
    /**
     * 小程序类型<br>
     * {@link com.xcky.enums.AccountTypeEnum}
     */
    private String appType;

    /**
     * 头条小程序可能会传输的字段
     */
    private String anonymousCode;
}
