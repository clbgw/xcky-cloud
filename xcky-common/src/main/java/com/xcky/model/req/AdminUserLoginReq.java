package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台系统登录请求参数
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class AdminUserLoginReq {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录类型(1-账号密码,2-邮箱密码,3-手机号码+密码)
     */
    private Integer loginType;
}
