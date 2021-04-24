package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 使用优惠券信息请求参数
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CouponInfoReq {
    /**
     * 券ID
     */
    private Long couponId;
    /**
     * 券编码
     */
    private String couponNo;

}
