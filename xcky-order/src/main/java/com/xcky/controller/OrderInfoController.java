package com.xcky.controller;

import com.github.pagehelper.PageInfo;
import com.xcky.model.req.OrderInfoPageReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.OrderInfoVo;
import com.xcky.service.OrderService;
import com.xcky.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单信息控制器
 *
 * @author lbchen
 */
@RestController
public class OrderInfoController {
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/orderInfo/list")
    public R getOrderInfoList(@RequestBody OrderInfoPageReq orderInfoPageReq) {
        PageInfo<OrderInfoVo> pageInfo = orderService.getOrderInfoVoPage(orderInfoPageReq);
        return ResponseUtil.ok(pageInfo);
    }
}
