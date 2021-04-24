package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.OrderDetailInfoMapper;
import com.xcky.model.entity.OrderDetailInfo;
import com.xcky.service.OrderDetailInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单详细信息服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class OrderDetailInfoServiceImpl implements OrderDetailInfoService {
    @Autowired
    private OrderDetailInfoMapper orderDetailInfoMapper;
    
    @Override
    public void saveOrderDetailInfo(OrderDetailInfo orderDetailInfo) {
        Integer insertResult = orderDetailInfoMapper.insertOrderDetailInfo(orderDetailInfo);
        if (insertResult < 1) {
            log.error("新增订单详细信息失败: orderInfo = " + JSONObject.toJSONString(orderDetailInfo));
            throw new BizException(ResponseEnum.SAVE_ORDER_DETAIL_FAIL, null);
        }
    }
    
    @Override
    public List<OrderDetailInfo> getOrderDetailInfosByOrderNo(String orderNo) {
        List<OrderDetailInfo> list = orderDetailInfoMapper.selectOrderDetailInfosByOrderNo(orderNo);
        return list;
    }

}
