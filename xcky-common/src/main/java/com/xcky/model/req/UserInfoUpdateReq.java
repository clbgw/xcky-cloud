package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户基本信息更新请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserInfoUpdateReq {
    /**
     * 主键ID,更新时必须带上此参数
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
}
