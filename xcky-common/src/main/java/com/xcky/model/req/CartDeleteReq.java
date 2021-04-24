package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 清空购物车请求对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartDeleteReq {

    /**
     * 用户ID
     */
    private Long userId;
}
