package com.xcky.model.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 头条小程序账号信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class MiniTtAccount {
    /**
     * 小程序密钥
     */
    private String secret;
    /**
     * 小程序名称
     */
    private String remark;
    /**
     * 是否启用(Y-启用,N-不启用)
     */
    private String status;
    /**
     * 头条账号类型
     */
    private String type = "ttMini";
}
