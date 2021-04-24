package com.xcky.model.wx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信获取openid的返回值
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxCode2sessionResp extends WxBaseResp {
    /**
     * 有效时间秒数
     */
    @JSONField(name = "expires_in")
    private Integer expiresIn;
    /**
     * 微信OPENID
     */
    private String openid;
    /**
     * 微信UNIONID
     */
    private String unionid;
    /**
     * 会话密钥
     */
    @JSONField(name = "session_key")
    private String sessionKey;

    /**
     * 头条小程序特有-匿名用户在当前小程序的 ID，如果请求时有 anonymous_code 参数才会返回
     */
    @JSONField(name = "anonymous_openid")
    private String anonymousOpenid;

    
    /********* 附加属性 ************/
    /**
     * 公众号唯一标识
     */
    private String appid;
}
