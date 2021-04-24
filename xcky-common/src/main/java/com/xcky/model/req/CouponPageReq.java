package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 优惠券分页列表请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CouponPageReq extends PageReq {
    /**
     * 用户ID
     */
    private Long userId;
}
