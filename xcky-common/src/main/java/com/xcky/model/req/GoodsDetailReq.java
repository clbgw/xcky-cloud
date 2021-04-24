package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品详情请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class GoodsDetailReq {
    /**
     * 商品ID
     */
    private Long goodsId;
}
