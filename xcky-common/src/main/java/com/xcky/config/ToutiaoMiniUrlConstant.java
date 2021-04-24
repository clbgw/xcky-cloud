package com.xcky.config;

/**
 * 头条小程序接口访问url配置
 *
 * @author lbchen
 */
public class ToutiaoMiniUrlConstant {
    /**
     * 头条小程序接口url前缀
     */
    private static String apiPrefix = "https://developer.toutiao.com";

    /********** GET 方式请求URL ***********/
    /**
     * 获取头条小程序openid的接口<br>
     *
     * @param appid 小程序编号
     * @param secret 小程序密钥
     */
    public static String GET_AUTH_CODE2SESSION_URI = apiPrefix + "/api/apps/jscode2session?appid=%s&secret=%s";

    /**
     * 获取头条小程序TOKEN的URL<br>
     * appid,secret
     */
    public static final String GET_ACCESS_TOKEN_URL_TEMPLATE = apiPrefix + "/api/apps/token?appid=%s&secret=%s&grant_type=client_credential";

}
