package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户微信收货地址信息请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserWxShippingInfoReq {
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 收货人姓名
     */
    private String userName;
    /**
     * 收货人手机号码
     */
    private String telNumber;
    /**
     * 收货地址国家码
     */
    private String nationalCode;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 国标收货地址第一级地址
     */
    private String provinceName;
    /**
     * 国标收货地址第二级地址
     */
    private String cityName;
    /**
     * 区域名称
     */
    private String countyName;
    /**
     * 详细收货地址信息
     */
    private String detailInfo;
    /************* 附加属性 ********************/
    /**
     * 用户ID
     */
    private Long userId;
}
