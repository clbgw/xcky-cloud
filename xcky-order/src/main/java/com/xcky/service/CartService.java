package com.xcky.service;

import com.xcky.model.req.CartChangeReq;
import com.xcky.model.req.CartDeleteItemReq;
import com.xcky.model.req.CartSaveItemReq;
import com.xcky.model.vo.CartVo;
import java.util.List;

/**
 * 购物车服务接口
 *
 * @author lbchen
 */
public interface CartService {

    /**
     * 保存购物车的数据
     *
     * @param userId           用户ID
     * @param cartSaveItemReqs 保存购物车项请求对象列表
     */
    void saveToCard(Long userId, List<CartSaveItemReq> cartSaveItemReqs);

    /**
     * 清空购物车
     *
     * @param userId 用户ID
     */
    void deleteCard(Long userId);

    /**
     * 删除部分购物车项
     *
     * @param cartDeleteItemReq 删除部分购物车项请求对象
     */
    void deleteCardItem(CartDeleteItemReq cartDeleteItemReq);

    /**
     * 修改购物车的商品项数量
     *
     * @param cartChangeReq 修改购物车数量请求对象
     */
    void changeCartItemNum(CartChangeReq cartChangeReq);

    /**
     * 根据用户ID获取购物车数据
     *
     * @param userId 用户ID
     * @param isSync 是否同步购物车商品详情
     * @return 购物车数据
     */
    CartVo getCartByUserId(Long userId, boolean isSync);
}
