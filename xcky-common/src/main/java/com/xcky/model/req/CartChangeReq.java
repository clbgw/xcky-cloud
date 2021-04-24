package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 改变购物车数量请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartChangeReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品数量
     */
    private Integer num;
}
