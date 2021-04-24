package com.xcky.util;

import java.math.BigDecimal;

/**
 * 全局常量
 *
 * @author lbchen
 */
public class Constants {

    public static final String SYSTEM_NAME = "小草开源后台管理系统";
    /**
     * 后台系统用户初始密码
     */
    public static final String USER_INIT_PWD = "CLBGW.vip";
    /**
     * 购物车KEY的前缀
     */
    public static final String CART_KEY_PREFIX = "CART:";
    /**
     * 随机字符串的全部字符
     */
    public static final String FULL_STR_CHAR = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    /**
     * 后台请求头accessToken默认超时秒数
     */
    public static final Integer DEFAULT_TOKEN_TIMEOUT = 900;
    /**
     * 后台请求头accessToken字段名
     */
    public static final String ACCESS_TOKEN_HEADER = "access_token";
    /**
     * 后台请求头accessToken字段名
     */
    public static final String TOKEN_FIELD = "token";
    /**
     * 未知
     */
    public static final String UNKNOWN = "unknown";
    /**
     * LOCAL IP
     */
    public static final String LOCAL_IP = "127.0.0.1";
    /**
     * 响应code字段名
     */
    public static final String CODE_FIELD = "code";
    /**
     * 响应desc字段名
     */
    public static final String DESC_FIELD = "desc";
    /**
     * 响应name字段名
     */
    public static final String NAME_FIELD = "name";
    /**
     * 响应msg字段名
     */
    public static final String MSG_FIELD = "msg";
    /**
     * 默认发件人邮箱
     */
    public static final String DEFAULT_FORM_EMAIL = "clb@clbgw.vip";
    /**
     * SHA256加密算法
     */
    public static final String HMAC_SHA_256 = "HmacSHA256";
    /**
     * JJWT的token密钥
     */
    public static final String TOKEN_KEY = "clb34554FJ.";
    /**
     * 字符串编码
     */
    public static final String CHARSET = "UTF-8";
    /**
     * 内容类型
     */
    public static final String CONTENT_TYPE = "Content-Type";
    /**
     * 请求头接受类型
     */
    public static final String ACCEPT = "Accept";

    /**
     * contentType的值
     */
    public static final String JSON_APPLICATION = "application/json;";
    /**
     * 文件格式-xml
     */
    public static final String XML = "xml";
    /**
     * xml标签
     */
    public static final String XML_TAG = "<xml>";

    /**
     * 证书实例名称
     */
    public static final String PKCS12 = "PKCS12";
    /**
     * MD5加密算法名称
     */
    public static final String MD5 = "MD5";

    public static final String SECURITY_PROVIDER = "BC";


    public static final String UNNION_PAY_SIGN_METHOD = "01";
    /**
     * 0.01元
     */
    public static final String PERCENT1 = "0.01";
    public static final String STR_ONE = "1";

    public static final int TWO = 2;
    public static final int EIGHT = 8;
    public static final int TEN = 10;
    public static final int FIF_TEEN = 15;
    public static final int SIX_TEEN = 16;
    public static final int THIRTY_TWO = 32;
    public static final int THIRTY_THREE = 33;
    public static final int SIXTY_FOUR = 64;
    public static final int SIXTY_EIGHT = 68;
    public static final int TWO_HUNDRED = 200;
    public static final BigDecimal DECIMAL_100 = new BigDecimal("100");

    public static final int H_1 = 0x01;

    public static final String SUCCESS_STR = "SUCCESS";
    public static final String FAIL_STR = "FAIL";
    public static final String NOTIFY_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    public static final String LEFT_BRACE = "{";
    public static final String RIGHT_BRACE = "}";
    public static final String UDERTLINE = "_";
    public static final String COMMA = ",";
    public static final String COLON = "|";
    public static final String POINT = ".";
    public static final String EQUAL = "=";
    public static final String Y = "Y";
    public static final String AMPERSAND = "&";
    public static final String AT = "@";
    public static final String DOT = ".";
    /**
     * 微信返回过来的字段名
     */
    public static final String FIELD_APPID = "appid";
    public static final String FIELD_RESULT_CODE = "result_code";
    public static final String FIELD_ERR_CODE_RES = "err_code_des";
    public static final String FIELD_WATERMARK = "watermark";
    public static final String FIELD_ERR_CODE = "err_code";
    public static final String FIELD_RETURN_CODE = "return_code";
    public static final String FIELD_RETURN_MSG = "return_msg";

    /**
     * 返回给小程序的字段名
     */
    public static final String FIELD_TOTAL_FEE = "total_fee";
    public static final String FIELD_OUT_TRADE_NO = "out_trade_no";
    public static final String FIELD_APP_ID = "appId";
    public static final String FIELD_MCH_ID = "mch_id";
    public static final String FIELD_SIGN = "sign";

    public static final String PARAM_CLASS_CODE = "param_status_type";


    public static final String VERSION_1_0_0 = "1.0.0";
    public static final String VERSION_5_0_0 = "5.0.0";
    public static final String VERSION_5_0_1 = "5.0.1";
    public static final String VERSION_5_1_0 = "5.1.0";
    public static final String SIGNMETHOD_RSA = "01";
    public static final String SIGNMETHOD_SHA256 = "11";
    public static final String SIGNMETHOD_SM3 = "12";
    public static final String UNIONPAY_CNNAME = "中国银联股份有限公司";
    /**
     * 敏感信息加密公钥
     */
    public static final String CERTTYPE_01 = "01";
    /**
     * 磁道加密公钥
     */
    public static final String CERTTYPE_02 = "02";


    /**
     * 批量文件内容.
     */
    public static final String PARAM_FILE_CONTENT = "fileContent";
    /**
     * 签名公钥证书
     */
    public static final String PARAM_SIGN_PUB_KEY_CERT = "signPubKeyCert";
    /**
     * 证书类型
     */
    public static final String PARAM_CERT_TYPE = "certType";
    /**
     * 加密公钥证书
     */
    public static final String PARAM_ENCRYPT_PUB_KEY_CERT = "encryptPubKeyCert";
    /**
     * 版本号.
     */
    public static final String PARAM_VERSION = "version";
    /**
     * 签名方法.
     */
    public static final String PARAM_SIGN_METHOD = "signMethod";
    /**
     * 证书ID.
     */
    public static final String PARAM_CERT_ID = "certId";
    /**
     * 签名.
     */
    public static final String PARAM_SIGNATURE = "signature";


}
