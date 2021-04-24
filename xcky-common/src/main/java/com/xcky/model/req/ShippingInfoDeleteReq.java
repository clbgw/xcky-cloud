package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 删除收货地址请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ShippingInfoDeleteReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 收货地址ID
     */
    private Long shippingId;
}
