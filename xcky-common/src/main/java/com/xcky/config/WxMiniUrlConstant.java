package com.xcky.config;

/**
 * 微信小程序接口访问url配置
 *
 * @author lbchen
 */
public class WxMiniUrlConstant {
    /**
     * 微信小程序接口url前缀
     */
    private static String apiPrefix = "https://api.weixin.qq.com";
    
    /********** GET 方式请求URL ***********/
    /**
     * 获取微信小程序openid的接口<br>
     *
     * @param appid 小程序编号
     * @param secret 小程序密钥
     * @param jsCode 前端给定
     */
    public static String GET_AUTH_CODE2SESSION_URI = apiPrefix + "/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";


}
