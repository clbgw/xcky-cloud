package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 后台系统修改登录密码
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class AdminUserUpdatePwdReq {
    /**
     * 旧密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String password;
    /**
     * 重复新密码
     */
    private String repassword;
    /**
     * token
     */
    private String token;
}
