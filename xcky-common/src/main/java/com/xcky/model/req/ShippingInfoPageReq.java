package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 收货地址信息分页请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ShippingInfoPageReq extends PageReq {
    /**
     * 用户ID
     */
    private Long userId;
}
