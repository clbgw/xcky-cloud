package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品分页列表请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class OrderInfoPageReq extends PageReq {
    /**
     * 微信公众号APPID
     */
    private String appid;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单状态
     * {@link com.xcky.enums.OrderStatusEnum}
     */
    private String orderStatus;
    /**
     * 订单类型
     * {@link com.xcky.enums.OrderTypeEnum}
     */
    private String orderType;
}
