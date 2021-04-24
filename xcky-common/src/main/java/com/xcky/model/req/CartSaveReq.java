package com.xcky.model.req;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 保存购物车请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartSaveReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 保存购物车项
     */
    private List<CartSaveItemReq> cartSaveItemReqs;
}
