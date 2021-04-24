package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.OrderInfo;
import com.xcky.model.req.OrderInfoPageReq;
import com.xcky.model.req.OrderInfoReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.OrderInfoVo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单服务接口
 *
 * @author lbchen
 */
public interface OrderService {
    /**
     * 执行下单请求
     *
     * @param orderInfoReq 下单请求对象
     * @return 返回对象
     */
    R custPlaceOrder(OrderInfoReq orderInfoReq);

    /**
     * 存储订单信息
     *
     * @param orderInfo 订单信息
     */
    void saveOrderInfo(OrderInfo orderInfo);

    /**
     * 查询订单信息列表
     *
     * @param orderInfoPageReq 订单分页请求参数
     * @return 订单信息分页列表
     */
    PageInfo<OrderInfoVo> getOrderInfoVoPage(OrderInfoPageReq orderInfoPageReq);

    /**
     * 判断订单中该用户该商品购买的次数是否符合
     *
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @param peerNum 该商品可购买次数
     */
    void judgeOrderGoodsPeerNum(Long userId, Long goodsId, BigDecimal peerNum);

    /**
     * 根据订单号查询订单信息
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    OrderInfoVo getOrderInfoVoByOrderNo(String orderNo);

    /**
     * 修改订单支付状态
     *
     * @param orderNo     订单编号
     * @param payOrderNo  支付订单编号
     * @param orderStatus 订单支付状态
     * @param updateTime  当前时间
     * @return 更新记录数
     */
    Integer updateOrderStatus(String orderNo, String payOrderNo, String orderStatus, Date updateTime);

    /**
     * 判断订单号是否存在
     *
     * @param orderNo 订单编号
     * @return 订单信息
     */
    OrderInfoVo judgeOrderNoExist(String orderNo);
}
