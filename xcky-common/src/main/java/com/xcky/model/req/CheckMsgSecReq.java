package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 检测内容是否敏感请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CheckMsgSecReq {
    /**
     * 微信APPID
     */
    private String appid;
    /**
     * 检测内容
     */
    private String msg;
}
