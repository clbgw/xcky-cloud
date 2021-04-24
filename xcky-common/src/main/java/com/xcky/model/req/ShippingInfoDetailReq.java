package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 收货地址详情请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ShippingInfoDetailReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 收货地址ID
     */
    private Long shippingId;
}
