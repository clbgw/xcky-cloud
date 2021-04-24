package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 第三方令牌信息实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ThirtTokenInfo {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 账号类型:wxmini-微信小程序
     */
    private String type;
    /**
     * 公众号APPID/商户号MCHID
     */
    private String appid;
    /**
     * 账号密钥
     */
    private String secret;
    /**
     * 名称备注
     */
    private String remark;
    /**
     * 登录邮箱(商户号直接扫码登录)
     */
    private String mail;
    /**
     * 登录密码(商户号直接扫码登录)
     */
    private String loginPwd;
    /**
     * 原始ID(商户号无原始ID)
     */
    private String orginId;
    /**
     * 会话令牌(商户号无令牌)
     */
    private String token;
    /**
     * 状态<br>
     * {@link com.xcky.enums.ThirtTokenStatusEnum}
     */
    private String status;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 第一次创建时间
     */
    private Date createTime;
    /**
     * 商户号API2密钥
     */
    private String apiSecret;
    /**
     * 商户号API3密钥
     */
    private String api3Secret;
    /**
     * 消息通知接口
     */
    private String notifyUrl;
    /**
     * 商户号退款证书文件路径
     */
    private String refundCertPath;
}
