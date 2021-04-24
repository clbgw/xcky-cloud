package com.xcky.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信商户账号信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class MchAccount {
    /**
     * 小程序名称
     */
    private String remark;
    /**
     * 是否启用(Y-启用,N-不启用)
     */
    private String status;
    /**
     * 商户号类型
     */
    private String type = "wxMch";
    /**
     * API的密钥
     */
    private String apiSecret;
    /**
     * API3的密钥
     */
    private String api3Secret;
    /**
     * 支付回调通知
     */
    private String notifyUrl;
    /**
     * 商户号退款证书文件路径
     */
    private String refundCertPath;
}
