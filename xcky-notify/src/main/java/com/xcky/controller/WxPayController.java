package com.xcky.controller;

import com.xcky.enums.OrderStatusEnum;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.vo.OrderInfoVo;
import com.xcky.service.OrderService;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.util.Constants;
import com.xcky.util.WxPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付通知控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class WxPayController {
    @Autowired
    private ThirtTokenInfoService thirtTokenInfoService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/pay")
    public String pay(@RequestBody String resultStr) {
        log.error("接收到的body请求参数:{}", resultStr);
        String returnStr = Constants.NOTIFY_SUCCESS;
        Date nowDate = new Date();
        try {
            // 如果body请求参数为json格式
            if (resultStr.startsWith(Constants.LEFT_BRACE)) {
                log.error("请求参数的body内容可能为json格式");
                return returnStr;
            }
            // 如果body的请求参数为非json格式
            else {
                Map<String, String> resultMap = WxPayUtil.xmlToMap(resultStr);
                String returnCode = resultMap.get(Constants.FIELD_RETURN_CODE);
                // 如果返回码不为success字符串
                if (!Constants.SUCCESS_STR.equalsIgnoreCase(returnCode)) {
                    String returnMsg = resultMap.get(Constants.FIELD_RETURN_MSG);
                    log.error(returnMsg);
                    return returnStr;
                }
                // 如果结果编码不为success字符串
                String resultCode = resultMap.get(Constants.FIELD_RESULT_CODE);
                if (!Constants.SUCCESS_STR.equalsIgnoreCase(resultCode)) {
                    String errCode = resultMap.get(Constants.FIELD_ERR_CODE);
                    String errCodeDes = resultMap.get(Constants.FIELD_ERR_CODE_RES);
                    log.error("业务结果不为SUCCESS: errCode=" + errCode + " , errCodeDes=" + errCodeDes);
                    return returnStr;
                }
                String mchId = resultMap.get(Constants.FIELD_MCH_ID);
                Map<String, Object> thirtTokenMap = new HashMap<>(8);
                thirtTokenMap.put(Constants.FIELD_APPID, mchId);
                ThirtTokenInfo thirtTokenInfo = thirtTokenInfoService.getThirtTokenInfoByMap(thirtTokenMap);
                if (null == thirtTokenInfo) {
                    log.error("请配置mchId = {} 的商户信息", mchId);
                    return returnStr;
                }
                String secret = thirtTokenInfo.getApiSecret();
                Map<String, String> signMap = resultMap;
                String sign = signMap.remove(Constants.FIELD_SIGN);
                // 生成签名字符串
                String genSign = WxPayUtil.generateSignature(signMap, secret);
                // 签名校验
                if (!("" + genSign).equals(sign)) {
                    log.error("签名验证失败: genSign = " + genSign + " , sign = " + sign);
                    return returnStr;
                }
                String payOrderNo = resultMap.get(Constants.FIELD_OUT_TRADE_NO);
                String orderNo = payOrderNo.split(Constants.UDERTLINE)[0];
                // 查询订单号是否存在
                OrderInfoVo orderInfoVo = orderService.getOrderInfoVoByOrderNo(orderNo);
                if (null == orderInfoVo) {
                    log.error("订单不存在: orderNo = " + orderNo);
                    return returnStr;
                }
                // 订单支付状态是否为待付
                String orderStatus = orderInfoVo.getOrderStatus();
                if (!OrderStatusEnum.WAIT.getCode().equals(orderStatus)) {
                    log.error("订单状态不正确: orderNo = {}, orderStatus = {}", orderNo, orderStatus);
                    return returnStr;
                }
                // 订单金额是否一致
                BigDecimal orderPayAmount = orderInfoVo.getOrderPayAmount();
                String wxOrderPayAmount = resultMap.get(Constants.FIELD_TOTAL_FEE);
                BigDecimal percent1Decimal = new BigDecimal(Constants.PERCENT1);
                if (orderPayAmount.subtract(new BigDecimal(wxOrderPayAmount)
                        .multiply(percent1Decimal))
                        .compareTo(percent1Decimal) >= 0) {
                    log.error("订单金额不一致: orderNo = {}, orderPayAmount = {}, wxOrderPayAmount = {}", orderNo, orderPayAmount, wxOrderPayAmount);
                    return returnStr;
                }

                // 修改订单支付状态
                Integer updateOrderStatusResult = orderService.updateOrderStatus(orderNo, payOrderNo, OrderStatusEnum.SUCCESS.getCode(), nowDate);
                if (null == updateOrderStatusResult || updateOrderStatusResult < 1) {
                    log.error("修改订单状态为已支付失败: orderNo = {}", orderNo);
                    return returnStr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }
}
