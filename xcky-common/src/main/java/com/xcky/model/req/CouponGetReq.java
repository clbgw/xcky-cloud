package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户领券请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CouponGetReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 券ID
     */
    private Long couponId;
}
