package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户更新基本信息请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserUpdateBaseInfoReq {
    /**
     * 加密数据
     */
    private String encryptedData;
    /**
     * 错误提示信息
     */
    private String errMsg;
    /**
     * 对称解密算法初始向量
     */
    private String iv;
    /**
     * 基本信息行展示
     */
    private String rawData;
    /**
     * 签名信息
     */
    private String signature;
    /**
     * 用户基本信息对象
     */
    private UserUpdateBaseSubInfoReq userInfo;

}
