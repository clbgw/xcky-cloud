package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.ResponseEnum;
import com.xcky.feigns.XckyUserFeignClients;
import com.xcky.model.entity.SourceLog;
import com.xcky.model.req.OrderGoodsInfoReq;
import com.xcky.model.req.OrderInfoReq;
import com.xcky.model.resp.R;
import com.xcky.service.OrderService;
import com.xcky.service.UserInfoService;
import com.xcky.util.ResponseUtil;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客户订单控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class CustOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private XckyUserFeignClients xckyUserFeignClients;
    /**
     * 下单接口
     *
     * @param orderInfoReq 下单请求对象
     * @return 响应对象
     */
    @PostMapping("/placeOrder")
    public R placeOrder(@RequestBody OrderInfoReq orderInfoReq) {
        if (null == orderInfoReq) {
            log.info("请求参数不能为空");
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        log.info(orderInfoReq.toString());
        List<OrderGoodsInfoReq> goodsList = orderInfoReq.getGoods();
        if (null == goodsList || goodsList.size() < 1) {
            log.info("商品列表不能为空");
            return ResponseUtil.fail(ResponseEnum.GOODS_EMPTY);
        }

        Long userId = orderInfoReq.getUserId();
        // 检查用户表中是否存在该用户ID,如果不存在此处将直接抛出异常
        userInfoService.judgeUserExist(userId);

        Date nowDate = new Date();
        SourceLog sourceLog = new SourceLog();
        sourceLog.setRecordDate(nowDate);
        sourceLog.setCreateTime(nowDate);
        sourceLog.setAppid("appid");
        sourceLog.setMsg("开始下单");
        sourceLog.setOpenid("openid");
        sourceLog.setUserId(userId);
        sourceLog.setUnionId("unionid");

        R logSaveR = xckyUserFeignClients.logSave(sourceLog);
        log.info(JSONObject.toJSONString(logSaveR));

        // 开始执行下单请求
        R r = orderService.custPlaceOrder(orderInfoReq);
        //R r = ResponseUtil.ok();

        nowDate = new Date();
        sourceLog.setRecordDate(nowDate);
        sourceLog.setCreateTime(nowDate);
        sourceLog.setMsg("结束下单：" + r.getMsg());
        logSaveR = xckyUserFeignClients.logSave(sourceLog);
        log.info(JSONObject.toJSONString(logSaveR));

        return r;
    }
}
