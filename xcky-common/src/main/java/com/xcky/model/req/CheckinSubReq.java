package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 签到订阅请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CheckinSubReq {
    /**
     * 公众号APPID
     */
    private String appid;
    /**
     * 微信OPENID
     */
    private String openid;
}
