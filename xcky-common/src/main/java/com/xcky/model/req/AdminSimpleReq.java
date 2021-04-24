package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 管理员简单请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class AdminSimpleReq {
    /**
     * 公众账号APPID
     */
    private String appid;
}
