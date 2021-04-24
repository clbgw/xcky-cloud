package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取购物车请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartGetReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 是否需要同步价格
     */
    private Boolean isSync = false;
}
