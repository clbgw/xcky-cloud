package com.xcky.service;

import com.xcky.model.entity.OrderDetailInfo;

import java.util.List;

/**
 * 订单明细信息服务接口
 *
 * @author lbchen
 */
public interface OrderDetailInfoService {
    /**
     * 新增订单明细信息
     *
     * @param orderDetailInfo 订单明细信息
     */
    void saveOrderDetailInfo(OrderDetailInfo orderDetailInfo);
    
    /**
     * 查询订单详情列表
     *
     * @param orderNo 订单编号
     * @return
     */
    List<OrderDetailInfo> getOrderDetailInfosByOrderNo(String orderNo);
    
}
