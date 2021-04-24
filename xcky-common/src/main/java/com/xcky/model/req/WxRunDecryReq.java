package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信运动解密请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxRunDecryReq {
    /**
     * 加密后的数据
     */
    private String encryData;
    /**
     * 初始化向量
     */
    private String iv;
    /**
     * 微信OPENID
     */
    private String openid;
    /******** 附加属性 **********/
    /**
     * 公众号标识,用于标识水印
     */
    private String appid;
    
}
