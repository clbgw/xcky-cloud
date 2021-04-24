package com.xcky.controller;

import com.xcky.enums.ResponseEnum;
import com.xcky.model.req.CartChangeReq;
import com.xcky.model.req.CartDeleteItemReq;
import com.xcky.model.req.CartDeleteReq;
import com.xcky.model.req.CartGetReq;
import com.xcky.model.req.CartSaveItemReq;
import com.xcky.model.req.CartSaveReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.CartVo;
import com.xcky.service.CartService;
import com.xcky.service.UserInfoService;
import com.xcky.util.ResponseUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车控制器
 *
 * @author lbchen
 */
@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 保存购物车的商品
     *
     * @param cartSaveReq 购物车保存请求对象
     * @return 返回对象
     */
    @PostMapping("/cart/save")
    public R saveToCard(@RequestBody CartSaveReq cartSaveReq) {
        if (null == cartSaveReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 判定用户是否在用户表中存在
        Long userId = cartSaveReq.getUserId();
        userInfoService.judgeUserExist(userId);

        List<CartSaveItemReq> cartSaveItemReqs = cartSaveReq.getCartSaveItemReqs();
        // 保存购物车信息
        cartService.saveToCard(userId, cartSaveItemReqs);
        return ResponseUtil.ok();
    }

    /**
     * 清空购物车
     *
     * @param cartDeleteReq 清空购物车请求对象
     * @return 返回对象
     */
    @PostMapping("/cart/delete")
    public R deleteCard(@RequestBody CartDeleteReq cartDeleteReq) {
        if (null == cartDeleteReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 判定用户是否在用户表中存在
        Long userId = cartDeleteReq.getUserId();
        userInfoService.judgeUserExist(userId);
        // 保存购物车信息
        cartService.deleteCard(userId);
        return ResponseUtil.ok();
    }


    /**
     * 删除部分购物车项
     *
     * @param cartDeleteItemReq 删除部分购物车项
     * @return 返回对象
     */
    @PostMapping("/cart/deleteItem")
    public R deleteCardItem(@RequestBody CartDeleteItemReq cartDeleteItemReq) {
        if (null == cartDeleteItemReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 判定用户是否在用户表中存在
        Long userId = cartDeleteItemReq.getUserId();
        userInfoService.judgeUserExist(userId);
        // 删除购物车项信息
        cartService.deleteCardItem(cartDeleteItemReq);
        return ResponseUtil.ok();
    }

    /**
     * 查看我的购物车(价格是否同步)
     *
     * @param cartGetReq 查看我的购物车请求参数
     * @return 返回对象
     */
    @PostMapping("/cart/get")
    public R getCart(@RequestBody CartGetReq cartGetReq) {
        if (null == cartGetReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 判定用户是否在用户表中存在
        Long userId = cartGetReq.getUserId();
        userInfoService.judgeUserExist(userId);
        // 修改购物车的商品项数量
        CartVo cartVo = cartService.getCartByUserId(userId, cartGetReq.getIsSync());
        return ResponseUtil.ok(cartVo);
    }


    /**
     * 修改购物车数量
     *
     * @param cartChangeReq 修改购物车数量请求对象
     * @return 返回对象
     */
    @PostMapping("/cart/change")
    public R changeCart(@RequestBody CartChangeReq cartChangeReq) {
        if (null == cartChangeReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 判定用户是否在用户表中存在
        Long userId = cartChangeReq.getUserId();
        userInfoService.judgeUserExist(userId);
        // 修改购物车的商品项数量
        cartService.changeCartItemNum(cartChangeReq);
        return ResponseUtil.ok();
    }
}
