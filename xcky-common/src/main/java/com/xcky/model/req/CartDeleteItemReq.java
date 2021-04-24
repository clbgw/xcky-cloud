package com.xcky.model.req;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 删除购物车中的商品请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartDeleteItemReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品ID列表
     */
    private List<Long> productIds;
}
