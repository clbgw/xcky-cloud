package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 订单详细信息列表请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class OrderDetailInfoDetailReq {
    /**
     * 订单编号
     */
    private String orderNo;
}
