package com.xcky.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信订阅号账号信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class SubAccount {
    /**
     * 微信订阅号密钥
     */
    private String secret;
    /**
     * 订阅号名称
     */
    private String remark;
    /**
     * 是否启用(Y-启用,N-不启用)
     */
    private String status;
    /**
     * 公众号类型
     */
    private String type = "wxSub";
    /**
     * 注册邮箱
     */
    private String mail;
    /**
     * 登录密码
     */
    private String loginPwd;
    /**
     * 原始ID
     */
    private String orginId;
}
