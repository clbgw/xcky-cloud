package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台请求用户信息列表请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class AdminUserInfoListReq extends PageReq {
    /**
     * APPID
     */
    private String appid;
    /**
     * 用户ID
     */
    private Long id;
}
