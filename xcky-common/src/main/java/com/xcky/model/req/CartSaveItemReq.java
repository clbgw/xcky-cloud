package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 保存购物车项请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartSaveItemReq {
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品数量
     */
    private Integer num;
}
