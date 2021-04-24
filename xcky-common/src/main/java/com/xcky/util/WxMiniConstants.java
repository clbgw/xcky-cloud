package com.xcky.util;

/**
 * 微信小程序常量
 *
 * @author lbchen
 */
public class WxMiniConstants {
    /**
     * 微信API接口调用URL前缀
     */
    private static final String WEIXIN_API_URL_PREFIX = "https://api.weixin.qq.com";
    /**
     * 微信商户API接口调用URL前缀
     */
    private static final String WEIXIN_PAY_URL_PREFIX = "https://api.mch.weixin.qq.com";

    /**
     * 内容风险检查的URL<br>
     * accessToken
     */
    public static final String POST_MSG_SEC_CHECK_TEMPLATE = WEIXIN_API_URL_PREFIX + "/wxa/msg_sec_check?access_token=%s";

    /**
     * 用户支付完成后，获取该用户的 UnionId，无需用户授权。本接口支持第三方平台代理查询。<br>
     * accessToken,openid
     */
    public static final String GET_PAID_UNIONID = WEIXIN_API_URL_PREFIX+"/wxa/getpaidunionid?access_token=%s&openid=%s";
    /**
     * 获取微信小程序TOKEN的URL<br>
     * appid,secret
     */
    public static final String GET_ACCESS_TOKEN_URL_TEMPLATE = WEIXIN_API_URL_PREFIX + "/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 发送小程序订阅模板<br>
     * accessToken
     */
    public static final String POST_SEND_SUBSCRIPT_TEMPLATE_URL_TEMPLATE = WEIXIN_API_URL_PREFIX + "/cgi-bin/message/subscribe/send?access_token=%s";
    /**
     * 生成小程序二维码<br>
     * accessToken
     */
    public static final String POST_WXQRCODE_UNLIMITED_URL_TEMPLATE = WEIXIN_API_URL_PREFIX + "/wxa/getwxacodeunlimit?access_token=%s";
    /**
     * 微信支付统一下单
     */
    public static final String POST_UNIFIEDORDER_URL = WEIXIN_PAY_URL_PREFIX + "/pay/unifiedorder";
    /**
     * 申请退款
     */
    public static final String POST_REFUND_URL = WEIXIN_PAY_URL_PREFIX + "/secapi/pay/refund";

}
