package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 收货地址信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ShippingInfo extends BaseSimpleTimeEntity {
    /**
     * 自增主键
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 地址字符串
     */
    private String address;
    
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
}
