package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.GoodsInfoStatusEnum;
import com.xcky.enums.OrderStatusEnum;
import com.xcky.enums.OrderTypeEnum;
import com.xcky.enums.ResponseEnum;
import com.xcky.enums.WxPayTradeTypeEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.OrderDetailInfo;
import com.xcky.model.entity.OrderInfo;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.req.GoodsDetailReq;
import com.xcky.model.req.WxMiniPayForOrderNoReq;
import com.xcky.model.req.WxMiniPayOrderReq;
import com.xcky.model.req.WxMiniRefundReq;
import com.xcky.model.req.WxMiniUnifiedorderReq;
import com.xcky.model.req.WxNativePayReq;
import com.xcky.model.req.WxPayGetPaidUnionIdReq;
import com.xcky.model.req.WxPayRefundReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.GoodsInfoVo;
import com.xcky.model.vo.OrderInfoVo;
import com.xcky.service.GoodsInfoService;
import com.xcky.service.OrderDetailInfoService;
import com.xcky.service.OrderService;
import com.xcky.service.ThirtTokenInfoService;
import com.xcky.service.UserInfoService;
import com.xcky.util.Constants;
import com.xcky.util.DateUtil;
import com.xcky.util.EntityUtil;
import com.xcky.util.NetworkUtil;
import com.xcky.util.ResponseUtil;
import com.xcky.util.RestTemplateUtil;
import com.xcky.util.StringUtil;
import com.xcky.util.WxMiniConstants;
import com.xcky.util.WxPayUtil;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class PayController {
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    ThirtTokenInfoService thirtTokenInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GoodsInfoService goodsInfoService;
    @Autowired
    private OrderDetailInfoService orderDetailInfoService;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "微信扫码支付")
    @PostMapping("/pay")
    public R pay(@RequestBody WxNativePayReq wxNativePayReq, HttpServletRequest request) {
        Long goodsId = wxNativePayReq.getGoodsId();
        GoodsInfoVo goodsInfoVo = goodsInfoService.getGoodsInfoByGoodsId(goodsId);
        if(null == goodsInfoVo) {
            throw new BizException(ResponseEnum.GOODS_EMPTY,null);
        }

        ThirtTokenInfo thirtTokenInfo = getMchAccountInfo(wxNativePayReq.getMchId());
        // 选定微信扫码支付
        WxPayTradeTypeEnum wxPayTradeTypeEnum = WxPayTradeTypeEnum.NATIVE;
        String remoteIp = NetworkUtil.getRemoteIp(request);
        String outTradeNo = StringUtil.generateNonceNum("" + DateUtil.getCurrentTimestampMs(), 4);
        String nonceStr = StringUtil.generateNonceStr(16);

        WxMiniUnifiedorderReq wxMiniUnifiedorderReq = new WxMiniUnifiedorderReq();
        wxMiniUnifiedorderReq.setTradeType(wxPayTradeTypeEnum.getCode());
        wxMiniUnifiedorderReq.setNonceStr(nonceStr);
        wxMiniUnifiedorderReq.setSpbillCreateIp(remoteIp);
        wxMiniUnifiedorderReq.setOutTradeNo(outTradeNo);

        wxMiniUnifiedorderReq.setNotifyUrl(thirtTokenInfo.getNotifyUrl());
        wxMiniUnifiedorderReq.setProductId(String.valueOf(goodsId));
        wxMiniUnifiedorderReq.setAppid(wxNativePayReq.getAppId());
        wxMiniUnifiedorderReq.setMchId(wxNativePayReq.getMchId());

        wxMiniUnifiedorderReq.setBody(goodsInfoVo.getGoodsName());
        // 数量处理
        Integer goodsNum = null == wxNativePayReq.getGoodsNum() || wxNativePayReq.getGoodsNum() <= 0  ? 1 : wxNativePayReq.getGoodsNum();
        // 总价(分) = 单价(元)* 100 * 数量
        Integer totalFee = goodsInfoVo.getCouponPrice().multiply(Constants.DECIMAL_100).intValue() * goodsNum;

        wxMiniUnifiedorderReq.setTotalFee(totalFee);
        Map<String, String> waitSignMap = EntityUtil.entityToStringMap(wxMiniUnifiedorderReq);
        String xmlStr = WxPayUtil.generateSignedXml(waitSignMap, thirtTokenInfo.getApiSecret());
        Map<String, String> resultMap = restTemplateUtil.remoteRequestForWx(WxMiniConstants.POST_UNIFIEDORDER_URL, xmlStr, null);
        if (null != resultMap) {
            log.info("微信扫码支付返回值:"+ JSONObject.toJSONString(resultMap));
            return ResponseUtil.ok(resultMap);
        }
        return ResponseUtil.fail(ResponseEnum.SERVER_ERROR);
    }

    @ApiOperation(value = "微信小程序-根据订单付款【二次付款】")
    @PostMapping("/payForOrderNo")
    public R payForOrder(@RequestBody WxMiniPayForOrderNoReq wxMiniPayForOrderNoReq) {
        String openid = wxMiniPayForOrderNoReq.getOpenid();
        String orderNo = wxMiniPayForOrderNoReq.getOrderNo();
        OrderInfoVo orderInfoVo = orderService.judgeOrderNoExist(orderNo);
        String orderOpenid = orderInfoVo.getOpenid();
        if (!openid.equals(orderOpenid)) {
            log.error("订单下单人与付款人openid不一致,orderOpenid = {},openid = {}", orderOpenid, openid);
            throw new BizException(ResponseEnum.ORDER_PAY_USER_VALID, null);
        }
        List<OrderDetailInfo> orderDetailInfoList = orderDetailInfoService.getOrderDetailInfosByOrderNo(orderNo);
        if (null == orderDetailInfoList || orderDetailInfoList.size() < 1) {
            log.error("该订单不存在订单明细,orderNo = {}", orderNo);
            throw new BizException(ResponseEnum.ORDER_DETAIL_NOT_EXIST, null);
        }
        Long goodsId = orderDetailInfoList.get(0).getGoodsId();
        // 获取商品信息
        GoodsInfoVo goodsInfoVo = goodsInfoService.getGoodsInfoByGoodsId(goodsId);
        if (null == goodsInfoVo || !goodsInfoVo.getGoodsStatus().equals(GoodsInfoStatusEnum.ENABLE.getCode())) {
            log.error("商品不存在或者已下架,goodsId = {}", goodsId);
            throw new BizException(ResponseEnum.GOODS_STATUS_NOT_ENABLE, null);
        }
        // 封装下单数据
        String appId = orderInfoVo.getAppid();
        String mchId = orderInfoVo.getMchId();
        Integer price = orderInfoVo.getOrderCouponPriceAmount().multiply(Constants.DECIMAL_100).intValue();
        String body = goodsInfoVo.getGoodsName();
        String oldPayOrderNo = orderInfoVo.getPayOrderNo();
        String payOrderNo;
        do {
            // 订单号需要生成新的订单号
            payOrderNo = StringUtil.generateNonceNum(orderNo + Constants.UDERTLINE, 6);
        } while (!StringUtils.isEmpty(oldPayOrderNo) && oldPayOrderNo.equals(payOrderNo));
        Map<String, String> payMap = callMiniPay(appId, mchId, openid, "" + goodsId, price, body, payOrderNo);
        // 封装返回的调用数据
        Long timestamp = DateUtil.getCurrentTimestamp();
        String signType = "MD5";
        getPayMap(payMap, timestamp, signType);
        return ResponseUtil.ok(payMap);
    }

    @ApiOperation(value = "微信小程序-直接下单付款【第一次下单】")
    @PostMapping("/miniPay")
    public R miniPay(@RequestBody WxMiniPayOrderReq wxMiniPayOrderReq) {
        Long userId = wxMiniPayOrderReq.getUserId();
        // 检查用户表中是否存在该用户ID,如果不存在此处将直接抛出异常
        userInfoService.judgeUserExist(userId);
        String productId = wxMiniPayOrderReq.getProductId();
        // 根据商品ID查询商品信息
        GoodsDetailReq goodsDetailReq = new GoodsDetailReq();
        goodsDetailReq.setGoodsId(Long.parseLong(productId));
        GoodsInfoVo goodsInfoVo = goodsInfoService.getGoodsInfoVoDetail(goodsDetailReq);
        if (null == goodsInfoVo) {
            log.error("找不到对应的产品, goodsId = " + productId);
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        String goodsStatus = goodsInfoVo.getGoodsStatus();
        Long goodsId = goodsInfoVo.getId();
        if (!GoodsInfoStatusEnum.ENABLE.getCode().equals(goodsStatus)) {
            log.error("当前商品已禁用, goodsId = " + goodsId);
            throw new BizException(ResponseEnum.GOODS_STATUS_NOT_ENABLE, null);
        }
        // 判断商品购买次数限制
        BigDecimal peerNum = goodsInfoVo.getPeerNum();
        orderService.judgeOrderGoodsPeerNum(userId, goodsId, peerNum);

        String body = goodsInfoVo.getGoodsName();
        String openid = wxMiniPayOrderReq.getOpenid();
        String appid = wxMiniPayOrderReq.getAppid();
        String price = wxMiniPayOrderReq.getPrice();
        String mchId = wxMiniPayOrderReq.getMchId();
        Long timestamp = DateUtil.getCurrentTimestamp();
        String signType = "MD5";
        String outTradeNo = StringUtil.generateNonceNum("" + timestamp, 4);
        // 页面的传参
        Integer priceInt = 1;
        BigDecimal buyNum = BigDecimal.ONE;
        BigDecimal priceBigDecimal = new BigDecimal(price);
        BigDecimal shouldPayPrice = goodsInfoVo.getCouponPrice();
        BigDecimal goodsPrice = goodsInfoVo.getPrice();
        if (priceBigDecimal.subtract(shouldPayPrice).abs().compareTo(new BigDecimal(Constants.PERCENT1)) > 0) {
            log.error("提示用户实付金额是否与应付金额不相等,实付金额 = " + priceBigDecimal + ",应付金额 = " + shouldPayPrice);
            return ResponseUtil.fail(ResponseEnum.ORDER_AMOUNT_ERROR);
        }
        if (!StringUtils.isEmpty(price)) {
            priceInt = priceBigDecimal.multiply(new BigDecimal("100")).intValue();
        }
        // 处理订单存储
        Date nowDate = new Date();
        // 设置订单详情
        OrderInfo orderInfo = dealOrderInfo(appid, mchId, outTradeNo, buyNum, nowDate, userId, openid, priceBigDecimal);
        // 实际支付总额
        orderInfo.setOrderPayAmount(goodsPrice);

        // 设置订单明细信息
        OrderDetailInfo orderDetailInfo = dealOderDetailInfo(buyNum, nowDate, goodsId, outTradeNo, priceBigDecimal,
                goodsInfoVo.getBeginDate(), goodsInfoVo.getEndDate(), shouldPayPrice,
                body, goodsInfoVo.getGoodsType(), goodsInfoVo.getGoodsUnit(), goodsPrice);
        Map<String, String> resultMap;
        try {
            // 调用微信支付下单
            resultMap = callMiniPay(appid, mchId, openid, productId, priceInt, body, outTradeNo);
            // 扣减商品库存
            goodsInfoService.reduceStock(goodsId, buyNum);
            // 存储订单明细
            orderDetailInfoService.saveOrderDetailInfo(orderDetailInfo);
            // 设置订单状态-预定是待支付订单
            orderInfo.setOrderStatus(OrderStatusEnum.WAIT.getCode());
            // 存储订单
            orderService.saveOrderInfo(orderInfo);
            // 封装返回的调用数据
            getPayMap(resultMap, timestamp, signType);
            return ResponseUtil.ok(resultMap);
        } catch (BizException e) {
            // 存储订单明细
            orderDetailInfoService.saveOrderDetailInfo(orderDetailInfo);
            // 设置订单状态-预定是待支付订单
            orderInfo.setOrderStatus(OrderStatusEnum.DEAL_FAIL.getCode());
            // 存储订单
            orderService.saveOrderInfo(orderInfo);
            throw e;
        }
    }

    /**
     * 处理订单信息
     *
     * @param appid           公众号APPID
     * @param mchId           商户号MCHID
     * @param outTradeNo      订单号
     * @param buyNum          购买数量
     * @param nowDate         当前时间
     * @param userId          用户ID
     * @param openid          微信OPENOD
     * @param priceBigDecimal 订单支付金额
     * @return 构建订单信息
     */
    private OrderInfo dealOrderInfo(String appid, String mchId, String outTradeNo, BigDecimal buyNum,
                                    Date nowDate, Long userId, String openid, BigDecimal priceBigDecimal) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setAppid(appid);
        orderInfo.setMchId(mchId);
        // 确定是虚拟订单
        orderInfo.setOrderType(OrderTypeEnum.VIRTUAL_ORDER.getCode());
        orderInfo.setOrderNo(outTradeNo);
        orderInfo.setGoodsBuyNum(buyNum);
        orderInfo.setCreateTime(nowDate);
        orderInfo.setUserId(userId);
        orderInfo.setOpenid(openid);

        orderInfo.setOrderCouponPriceAmount(priceBigDecimal);
        orderInfo.setOrderPriceAmount(priceBigDecimal);
        return orderInfo;
    }

    /**
     * 处理订单详情信息
     *
     * @param buyNum          购买数量
     * @param nowDate         当前时间
     * @param goodsId         商品ID
     * @param outTradeNo      订单编号
     * @param priceBigDecimal 价格
     * @param beginDate       商品开始时间
     * @param endDate         商品结束时间
     * @param couponPrice     优惠价格
     * @param goodsName       商品名称
     * @param goodsType       商品类型
     * @param goodsUnit       商品单位
     * @param price           商品价格
     * @return 订单详情信息
     */
    private OrderDetailInfo dealOderDetailInfo(BigDecimal buyNum, Date nowDate, Long goodsId, String outTradeNo,
                                               BigDecimal priceBigDecimal, Date beginDate, Date endDate,
                                               BigDecimal couponPrice, String goodsName, String goodsType,
                                               String goodsUnit, BigDecimal price) {
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
        orderDetailInfo.setBuyNum(buyNum);
        orderDetailInfo.setCreateTime(nowDate);
        orderDetailInfo.setGoodsId(goodsId);
        orderDetailInfo.setOrderNo(outTradeNo);
        orderDetailInfo.setPayPrice(priceBigDecimal);
        orderDetailInfo.setBeginDate(beginDate);
        orderDetailInfo.setEndDate(endDate);
        orderDetailInfo.setCouponPrice(couponPrice);
        orderDetailInfo.setGoodsName(goodsName);
        orderDetailInfo.setGoodsType(goodsType);
        orderDetailInfo.setGoodsUnit(goodsUnit);
        orderDetailInfo.setPrice(price);
        return orderDetailInfo;
    }

    @ApiOperation(value = "微信支付退款")
    @PostMapping("/wxPayRefund")
    public R wxPayRefund(@RequestBody WxPayRefundReq wxPayRefundReq) {
        String orderNo = wxPayRefundReq.getOutTradeNo();

        Integer totalFee = wxPayRefundReq.getTotalFee();
        Integer refundFee = wxPayRefundReq.getRefundFee();
        // 退款金额不应该大于总金额
        if(totalFee < refundFee) {
            throw new BizException(ResponseEnum.PARAM_ERROR,null);
        }
        String mchId = wxPayRefundReq.getMchId();
        // 获取匹配的商户信息
        ThirtTokenInfo thirtTokenInfo = getMchAccountInfo(mchId);
        String refundCertPath = thirtTokenInfo.getRefundCertPath();
        String notifyUrl = thirtTokenInfo.getNotifyUrl();

        WxMiniRefundReq wxMiniRefundReq = new WxMiniRefundReq();
        wxMiniRefundReq.setNonceStr(StringUtil.generateNonceStr(16));
        wxMiniRefundReq.setOutRefundNo(StringUtil.generateNonceStr(16));
        wxMiniRefundReq.setAppid(wxPayRefundReq.getAppId());
        wxMiniRefundReq.setMchId(mchId);
        wxMiniRefundReq.setOutTradeNo(orderNo);
        wxMiniRefundReq.setTotalFee(String.valueOf(totalFee));
        wxMiniRefundReq.setRefundFee(String.valueOf(refundFee));
        wxMiniRefundReq.setNotifyUrl(notifyUrl);

        Map<String, String> map = EntityUtil.entityToStringMap(wxMiniRefundReq);
        String xmlStr = WxPayUtil.generateSignedXml(map, thirtTokenInfo.getApiSecret());
        Map<String, String> resultMap = restTemplateUtil.doRefund(mchId, WxMiniConstants.POST_REFUND_URL, xmlStr, refundCertPath);
        if (null != resultMap) {
            return ResponseUtil.ok(resultMap);
        }
        return ResponseUtil.fail(ResponseEnum.SERVER_ERROR);
    }


    @ApiOperation(value = "微信支付后获取用户UNIONID")
    @PostMapping("/getPaidUnionId")
    public R getPaidUnionId(@RequestBody WxPayGetPaidUnionIdReq wxPayGetPaidUnionIdReq) {



        return ResponseUtil.ok();
    }


    /**
     * 生成小程序支付的付款参数
     *
     * @param resultMap 微信支付下单返回数据
     * @param timestamp 当前时间戳
     * @param signType  签名类型
     */
    private void getPayMap(Map<String, String> resultMap, Long timestamp, String signType) {
        // 生成支付签名
        String nonceStrRtn = resultMap.get("nonce_str");
        String packageStr = "prepay_id=" + resultMap.get("prepay_id");
        Map<String, String> waitSignMap = new HashMap<>(16);
        waitSignMap.put(Constants.FIELD_APP_ID, resultMap.get("appid"));
        waitSignMap.put("timeStamp", "" + timestamp);
        waitSignMap.put("nonceStr", nonceStrRtn);
        waitSignMap.put("package", packageStr);
        waitSignMap.put("signType", signType);
        String paySign = WxPayUtil.generateSignature(waitSignMap, resultMap.get("apiSecret"));
        // 补充页面额外需要的变量
        resultMap.put("timeStamp", "" + timestamp);
        resultMap.put("paySign", paySign);
    }

    /**
     * 调用小程序支付下单
     *
     * @param appid      小程序APPID
     * @param mchId      商户号
     * @param openid     微信OPENID
     * @param productId  商品编号
     * @param price      订单应付金额
     * @param body       订单显示的内容
     * @param outTradeNo 订单编号
     * @return 调用小程序支付下单返回值
     */
    private Map<String, String> callMiniPay(
            String appid,
            String mchId,
            String openid,
            String productId,
            Integer price,
            String body,
            String outTradeNo
    ) {
        // 获取匹配的商户信息
        ThirtTokenInfo thirtTokenInfo = getMchAccountInfo(mchId);
        String notifyUrl = thirtTokenInfo.getNotifyUrl();
        if (StringUtils.isEmpty(notifyUrl)) {
            log.error("请配置 mchId = {} 的商户信息中的支付回调【notifyUrl】参数", mchId);
            throw new BizException(ResponseEnum.SERVER_ERROR, null);
        }
        String apiSecret = thirtTokenInfo.getApiSecret();
        if (StringUtils.isEmpty(apiSecret)) {
            log.error("请配置 mchId = {} 的商户信息中的支付回调【apiSecret】参数", mchId);
            throw new BizException(ResponseEnum.SERVER_ERROR, null);
        }
        // 封装下单请求参数
        WxMiniUnifiedorderReq wxMiniUnifiedorderReq = new WxMiniUnifiedorderReq();
        wxMiniUnifiedorderReq.setAppid(appid);
        wxMiniUnifiedorderReq.setMchId(mchId);
        wxMiniUnifiedorderReq.setOpenid(openid);
        wxMiniUnifiedorderReq.setNotifyUrl(notifyUrl);
        // 页面传参关联的参数
        wxMiniUnifiedorderReq.setProductId(productId);
        wxMiniUnifiedorderReq.setTotalFee(price);
        wxMiniUnifiedorderReq.setBody(body);
        // 特定形式的入参
        String nonceStr = StringUtil.generateNonceStr(16);
        wxMiniUnifiedorderReq.setNonceStr(nonceStr);
        wxMiniUnifiedorderReq.setOutTradeNo(outTradeNo);
        wxMiniUnifiedorderReq.setTradeType(WxPayTradeTypeEnum.JSAPI.getCode());
        // 调用下单接口的服务器IP
        String spbillCreateIp = NetworkUtil.getInnetIp();
        wxMiniUnifiedorderReq.setSpbillCreateIp(spbillCreateIp);

        log.info("调用微信统一下单接口请求参数:{}", JSONObject.toJSONString(wxMiniUnifiedorderReq));
        Map<String, String> waitSignMap = EntityUtil.entityToStringMap(wxMiniUnifiedorderReq);
        String xmlStr = WxPayUtil.generateSignedXml(waitSignMap, apiSecret);
        Map<String, String> resultMap = restTemplateUtil.remoteRequestForWx(WxMiniConstants.POST_UNIFIEDORDER_URL, xmlStr, null);
        if (null != resultMap) {
            if (Constants.FAIL_STR.equals(resultMap.get(Constants.FIELD_RESULT_CODE))) {
                log.error("微信统一下单返回值: {}", JSONObject.toJSONString(resultMap));
                throw new BizException(ResponseEnum.CALL_WXPAY_ERROR, resultMap.get(Constants.FIELD_ERR_CODE_RES));
            }
            resultMap.put("apiSecret", apiSecret);
            return resultMap;
        } else {
            log.error("调用微信支付统一下单接口返回空对象");
            throw new BizException(ResponseEnum.CALL_WXPAY_ERROR, null);
        }
    }

    /**
     * 根据商户号获取商户信息
     *
     * @param mchId 商户号
     * @return 商户号信息
     */
    private ThirtTokenInfo getMchAccountInfo(String mchId) {
        Map<String, Object> mchAccountMap = new HashMap<>(4);
        mchAccountMap.put(Constants.FIELD_APPID, mchId);
        ThirtTokenInfo thirtTokenInfo = thirtTokenInfoService.getThirtTokenInfoByMap(mchAccountMap);
        if (null == thirtTokenInfo) {
            log.error("请配置mchId = {} 的商户信息", mchId);
            throw new BizException(ResponseEnum.SERVER_ERROR, null);
        }
        return thirtTokenInfo;
    }
}
