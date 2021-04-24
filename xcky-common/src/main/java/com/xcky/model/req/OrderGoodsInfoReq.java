package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 下单商品信息请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class OrderGoodsInfoReq {
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 购买商品数量
     */
    private BigDecimal quantity;
    
    //TODO 商品代金券
    
}
