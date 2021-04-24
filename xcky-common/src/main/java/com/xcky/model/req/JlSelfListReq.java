package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取个人简历列表请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlSelfListReq {
    /**
     * 微信OPENID
     */
    private String openid;

    /**
     * 用户账号ID
     */
    private Long userId;
}
