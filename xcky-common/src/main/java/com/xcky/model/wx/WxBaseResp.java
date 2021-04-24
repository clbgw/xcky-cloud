package com.xcky.model.wx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信调用接口的基本返回值
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxBaseResp {
    /**
     * 错误返回码
     */
    private Integer errcode;

    /**
     * 错误消息提示
     */
    private String errmsg;



    /**
     * 头条小程序返回值字段，0代表成功
     */
    private Integer error;

    /**
     * 头条小程序返回值字段，错误信息（同 errmsg）
     */
    private String message;
}
