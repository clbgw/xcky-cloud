package com.xcky.controller;

import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.OrderDetailInfo;
import com.xcky.model.req.OrderDetailInfoDetailReq;
import com.xcky.model.resp.R;
import com.xcky.service.OrderDetailInfoService;
import com.xcky.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单详细信息控制器
 *
 * @author lbchen
 */
@RestController
public class OrderDetailInfoController {
    @Autowired
    private OrderDetailInfoService orderDetailInfoService;
    
    @PostMapping("/orderDetail/detail")
    public R getOrderDetailInfo(@RequestBody OrderDetailInfoDetailReq orderDetailInfoDetailReq) {
        String orderNo = orderDetailInfoDetailReq.getOrderNo();
        if(StringUtils.isEmpty(orderNo)) {
            throw new BizException(ResponseEnum.PARAM_ERROR,null);
        }
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.getOrderDetailInfosByOrderNo(orderNo);
        return ResponseUtil.ok(orderDetailInfoList);
    }
}
