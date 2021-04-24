package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.config.OrderConfig;
import com.xcky.enums.CouponStatusEnum;
import com.xcky.enums.CouponUseStatusEnum;
import com.xcky.enums.OrderStatusEnum;
import com.xcky.enums.OrderTypeEnum;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.CouponInfoMapper;
import com.xcky.mapper.GoodsInfoMapper;
import com.xcky.mapper.OrderDetailInfoMapper;
import com.xcky.mapper.OrderInfoMapper;
import com.xcky.mapper.ShippingInfoMapper;
import com.xcky.mapper.UserCouponRelaMapper;
import com.xcky.mapper.UserInfoMapper;
import com.xcky.model.entity.GoodsInfo;
import com.xcky.model.entity.OrderDetailInfo;
import com.xcky.model.entity.OrderInfo;
import com.xcky.model.entity.ShippingInfo;
import com.xcky.model.entity.UserCouponRela;
import com.xcky.model.req.CouponInfoReq;
import com.xcky.model.req.OrderGoodsInfoReq;
import com.xcky.model.req.OrderInfoPageReq;
import com.xcky.model.req.OrderInfoReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.CouponInfoVo;
import com.xcky.model.vo.OrderInfoVo;
import com.xcky.service.OrderService;
import com.xcky.util.Constants;
import com.xcky.util.DateUtil;
import com.xcky.util.EntityUtil;
import com.xcky.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 订单服务接口实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderConfig orderConfig;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    @Autowired
    private ShippingInfoMapper shippingInfoMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OrderDetailInfoMapper orderDetailInfoMapper;
    @Autowired
    private CouponInfoMapper couponInfoMapper;
    @Autowired
    private UserCouponRelaMapper userCouponRelaMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R custPlaceOrder(OrderInfoReq orderInfoReq) {
        String orderType = orderInfoReq.getOrderType();
        Long userId = orderInfoReq.getUserId();
        
        ShippingInfo shippingInfo = null;
        // 判断是否为实物订单
        if (OrderTypeEnum.PHYSICAL_ORDER.getCode().equals(orderType)) {
            // 根据用户ID和收货地址ID鉴定用户收货地址
            Long shippingInfoId = orderInfoReq.getShippingId();
            List<ShippingInfo> shippingInfoList = shippingInfoMapper.selectShippingInfoByKey(userId, shippingInfoId);
            if (null == shippingInfoList || shippingInfoList.size() < 1) {
                log.info("收货地址有误,用户ID = " + userId + " , shippingId = " + shippingInfoId);
                return ResponseUtil.fail(ResponseEnum.USER_SHIPPING_ERROR);
            } else {
                shippingInfo = shippingInfoList.get(0);
            }
        }
        
        // 计算的商品总额
        BigDecimal computePriceAmount = BigDecimal.ZERO;
        // 计算的商品折后总额(扣除代金券的金额)
        BigDecimal computeCouponPriceAmount = BigDecimal.ZERO;
        // 计算的购买商品总量
        BigDecimal computeNumAmount = BigDecimal.ZERO;
        Set<Long> goodsIdSet = new HashSet<>();
        Map<Long, BigDecimal> goodsNumMap = new HashMap<>(8);
        List<OrderGoodsInfoReq> goodsList = orderInfoReq.getGoods();
        for (OrderGoodsInfoReq goods : goodsList) {
            BigDecimal quantity = goods.getQuantity();
            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                log.info("购买商品的数量有误，购买的数量小于等于0");
                return ResponseUtil.fail(ResponseEnum.ORDER_NUM_ERROR);
            }
            // 统计用户购买商品数量
            computeNumAmount = computeNumAmount.add(quantity);
            Long goodsId = goods.getGoodsId();
            // 累计商品编码ID
            goodsIdSet.add(goodsId);
            
            if (goodsNumMap.containsKey(goodsId)) {
                if (goodsNumMap.get(goodsId).compareTo(BigDecimal.ZERO) > 0) {
                    goodsNumMap.put(goodsId, goodsNumMap.get(goodsId).add(quantity));
                } else {
                    goodsNumMap.put(goodsId, quantity);
                }
            } else {
                goodsNumMap.put(goodsId, quantity);
            }
        }
        // 订单商品数量核对【订单商品数量与计算的商品数量是否一致】
        BigDecimal goodsBuyNum = orderInfoReq.getGoodsBuyNum();
        if (goodsBuyNum.compareTo(BigDecimal.ZERO) < 0 || goodsBuyNum.compareTo(computeNumAmount) != 0) {
            log.info("提示订单商品数量与计算的商品数量不一致");
            return ResponseUtil.fail(ResponseEnum.ORDER_NUM_ERROR);
        }
        // 根据商品ID列表查询商品信息【商品编号,商品名称,商品单位,商品单价,商品折后价,商品库存,每人每次限购库存,商品类型,商品开始有效期,商品结束有效期,商品状态】列表
        List<GoodsInfo> goodsInfoList = goodsInfoMapper.selectGoodsInfoBySet(goodsIdSet);
        if (null == goodsInfoList || goodsInfoList.size() < 1) {
            log.info("查询商品种类为空");
            return ResponseUtil.fail(ResponseEnum.ORDER_NUM_ERROR);
        }
        if (goodsIdSet.size() != goodsInfoList.size()) {
            // 提示部分商品选择有误
            log.info("所选商品种类 " + goodsIdSet.size() + ", 查询商品种类 " + goodsInfoList.size() + " ,种类数量不一致");
            return ResponseUtil.fail(ResponseEnum.ORDER_NUM_ERROR);
        }
        Date nowDate = new Date();
        for (GoodsInfo goodsInfo : goodsInfoList) {
            // 判断当前日期是否在可购买日期内
            Date beginDate = goodsInfo.getBeginDate();
            Date endDate = goodsInfo.getEndDate();
            if (beginDate.compareTo(nowDate) >= 0 || endDate.compareTo(nowDate) <= 0) {
                // 提示有商品为当前不可购时间区间
                log.info("当前时间:" + DateUtil.getTimeStrByDate(nowDate) +
                        ", 商品可购时间: 【" + DateUtil.getTimeStrByDate(beginDate) + " ~ "
                        + DateUtil.getTimeStrByDate(endDate) + "】");
                return ResponseUtil.fail(ResponseEnum.BUY_TIME_OUT_OF_RANGE);
            }
            
            Long goodsId = goodsInfo.getId();
            BigDecimal goodsNum = goodsNumMap.get(goodsId);
            // 判断每人每次可购数量是否超过限制
            if (goodsNum.compareTo(goodsInfo.getPeerNum()) > 0) {
                // 提示每人每次可购数量超量
                log.info("单次可购超量,单次可购库存 = " + goodsInfo.getPeerNum() + ", 购买库存 = " + goodsNum);
                return ResponseUtil.fail(ResponseEnum.PEER_STORE_OUT_OF_RANGE);
            }
            // 判断库存是否充足
            if (goodsNum.compareTo(goodsInfo.getLeftNum()) > 0) {
                // 提示库存不足
                log.info("库存不足:goodsId = " + goodsId + ", 剩余库存 = " + goodsInfo.getLeftNum() + ", 购买库存 = " + goodsNum);
                return ResponseUtil.fail(ResponseEnum.STORE_NOT_ENOUGH);
            }
            // 统计商品总额(不计算折扣和优惠券)
            computePriceAmount = computePriceAmount.add(goodsInfo.getPrice().multiply(goodsNum));
            // 统计商品折扣总额(计算折扣不计算优惠券)
            computeCouponPriceAmount = computeCouponPriceAmount.add(goodsInfo.getCouponPrice().multiply(goodsNum));
        }
        
        List<CouponInfoVo> selectCoupons = new ArrayList<>();
        // 请求参数中的代金券
        List<CouponInfoReq> coupons = orderInfoReq.getCoupons();
        if (null != coupons && coupons.size() > 0) {
            // 根据用户ID查询目前可使用的优惠券列表
            Map<String, Object> couponInfoMap = new HashMap<>(8);
            couponInfoMap.put("userId", userId);
            couponInfoMap.put("useStatus", CouponUseStatusEnum.COLLECTED.getCode());
            couponInfoMap.put("couponStatus", CouponStatusEnum.ON_SHELF.getCode());
            List<CouponInfoVo> couponInfos = couponInfoMapper.selectUserCouponByMap(couponInfoMap);
            if (coupons.size() > couponInfos.size()) {
                log.error("用户含有非法的券: 已领的券数量 = " + couponInfos.size() + ", 用户选择的券数量: " + coupons.size());
                return ResponseUtil.fail(ResponseEnum.INVALID_COUPON_SELECTED);
            }
            
            // 过滤出匹配的优惠券(已领的和选择的券)
            for (CouponInfoReq couponInfoReq : coupons) {
                if (null == couponInfoReq) {
                    continue;
                }
                for (CouponInfoVo couponVo : couponInfos) {
                    if (null == couponVo) {
                        continue;
                    }
                    if (couponInfoReq.getCouponId().equals(couponVo.getId())
                            && couponInfoReq.getCouponNo().equals(couponVo.getCouponNo())) {
                        selectCoupons.add(couponVo);
                        break;
                    }
                }
            }
        }
        
        // 扣减库存
        for (Long goodsId : goodsNumMap.keySet()) {
            if (null == goodsId) {
                continue;
            }
            BigDecimal quantity = goodsNumMap.get(goodsId);
            Integer updateResult = goodsInfoMapper.updateStore(goodsId, quantity, nowDate);
            if (null == updateResult || updateResult < 1) {
                // 提示扣减库存失败
                log.info("扣减库存失败: 商品ID = " + goodsId + " , 需要扣除的商品数量 = " + quantity);
                // 此处必须是抛出异常才能事务回滚
                throw new BizException(ResponseEnum.DIS_STORE_FAIL, null);
            }
        }
        
        // 用户待支付的金额扣减积分抵扣的金额
        BigDecimal userIntegralAmount = orderInfoReq.getIntegralAmount();
        if (null != userIntegralAmount) {
            // 判断用户积分数是否足够多
            BigDecimal leftIntegral = userInfoMapper.selectUserIntegeral(userId, userIntegralAmount);
            if (leftIntegral.compareTo(BigDecimal.ZERO) < 0) {
                log.info("用户积分数不足: 不够的积分为 :" + BigDecimal.ZERO.subtract(leftIntegral));
                return ResponseUtil.fail(ResponseEnum.NOT_ENOUGH_INTEGRAL);
            }
        }
        computeCouponPriceAmount = computeCouponPriceAmount.subtract(orderConfig.getIntegralMulti().multiply(userIntegralAmount));
        
        // 计算用户实付金额
        BigDecimal computePayAmount = computeCouponPriceAmount;
        //TODO 商品代金券优惠计算
        
        //TODO 订单代金券优惠计算
        
        // 判断用户实付金额是否与应付金额相等
        BigDecimal payAmount = orderInfoReq.getPayAmount();
        if (computePayAmount.subtract(payAmount).abs().compareTo(new BigDecimal(Constants.PERCENT1)) > 0) {
            log.info("提示用户实付金额是否与应付金额不相等,实付金额 = " + payAmount + ",应付金额 = " + computeCouponPriceAmount);
            return ResponseUtil.fail(ResponseEnum.ORDER_AMOUNT_ERROR);
        }
        
        // 生成订单编号
        String nowDateStr = DateUtil.getTimeStrByDate(nowDate, DateUtil.DATE_TIME_SIMPLE_PATTERN);
        String orderNo = nowDateStr + RandomStringUtils.randomNumeric(10);
        // 保存订单信息(待支付)
        OrderInfo orderInfo = new OrderInfo();
        
        String appid = orderInfoReq.getAppid();
        if (!StringUtils.isEmpty(appid)) {
            orderInfo.setAppid(orderInfoReq.getAppid());
        }
        String mchId = orderInfoReq.getMchId();
        if (!StringUtils.isEmpty(mchId)) {
            orderInfo.setMchId(orderInfoReq.getMchId());
        }
        
        orderInfo.setOrderType(orderType);
        orderInfo.setOrderNo(orderNo);
        orderInfo.setOrderStatus(OrderStatusEnum.WAIT.getCode());
        orderInfo.setCreateTime(nowDate);
        orderInfo.setUserId(userId);
        // 订单数量部分
        orderInfo.setGoodsBuyNum(goodsBuyNum);
        // 金额部分
        orderInfo.setOrderPriceAmount(computePriceAmount);
        orderInfo.setOrderCouponPriceAmount(computeCouponPriceAmount);
        orderInfo.setOrderPayAmount(computePayAmount);
        // 收货地址部分
        if (null != shippingInfo) {
            String address = shippingInfo.getAddress();
            orderInfo.setShippingAddress(address);
        }
        // 新增订单信息
        Integer saveOrderResult = orderInfoMapper.insertOrderInfo(orderInfo);
        if (null == saveOrderResult || saveOrderResult < 1) {
            log.info("新增订单信息失败: orderInfo = " + JSONObject.toJSONString(orderInfo));
            throw new BizException(ResponseEnum.SAVE_ORDER_FAIL, null);
        }
        //TODO 保存订单使用的优惠券信息
        
        //TODO 代金券从已领用改成已使用
        if (null != selectCoupons && selectCoupons.size() > 0) {
            UserCouponRela userCouponRela = null;
            for (CouponInfoVo couponInfoVo : selectCoupons) {
                if (null == couponInfoVo) {
                    continue;
                }
                // 更新用户领券关系表状态
                userCouponRela = new UserCouponRela();
                userCouponRela.setId(couponInfoVo.getUserCouponId());
                userCouponRela.setUpdateTime(nowDate);
                userCouponRela.setUseStatus(CouponUseStatusEnum.USED.getCode());
                Integer updateCouponUseStatus = userCouponRelaMapper.updateUserCouponStatus(userCouponRela);
                if (null == updateCouponUseStatus || updateCouponUseStatus < 1) {
                    log.info("更新用户代金券关系使用状态失败: userCouponRela = " + JSONObject.toJSONString(userCouponRela));
                    throw new BizException(ResponseEnum.UPDATE_COUPON_USE_STATUS, null);
                }
            }
        }
        
        // 从用户信息中扣减积分数
        if (null != userIntegralAmount) {
            Integer updateIntegralResult = userInfoMapper.updateUserIntegral(userId, userIntegralAmount);
            if (null == updateIntegralResult || updateIntegralResult < 1) {
                log.info("用户积分数不足: 用户ID = " + userId + " , 使用的积分 = " + userIntegralAmount);
                throw new BizException(ResponseEnum.NOT_ENOUGH_INTEGRAL, null);
            }
        }
        
        // 保存订单明细信息
        OrderDetailInfo orderDetailInfo = null;
        Integer saveOrderDetailResult = 0;
        for (GoodsInfo goodsInfo : goodsInfoList) {
            if (null == goodsInfo) {
                continue;
            }
            Long goodsId = goodsInfo.getId();
            // 取出总共购买该商品的数量
            BigDecimal quantity = goodsNumMap.get(goodsId);
            
            orderDetailInfo = new OrderDetailInfo();
            orderDetailInfo.setGoodsId(goodsId);
            orderDetailInfo.setGoodsName(goodsInfo.getGoodsName());
            orderDetailInfo.setGoodsUnit(goodsInfo.getGoodsUnit());
            /**
             * 原单价
             */
            orderDetailInfo.setPrice(goodsInfo.getPrice());
            /**
             * 折扣后单价
             */
            orderDetailInfo.setCouponPrice(goodsInfo.getCouponPrice());
            orderDetailInfo.setGoodsType(goodsInfo.getGoodsType());
            orderDetailInfo.setBeginDate(goodsInfo.getBeginDate());
            orderDetailInfo.setEndDate(goodsInfo.getEndDate());
            // 填充需要加工的数据
            orderDetailInfo.setBuyNum(quantity);
            orderDetailInfo.setCreateTime(nowDate);
            orderDetailInfo.setOrderNo(orderNo);
            
            //TODO 计算出实付金额
            orderDetailInfo.setPayPrice(goodsInfo.getCouponPrice().multiply(quantity));
            
            saveOrderDetailResult = orderDetailInfoMapper.insertOrderDetailInfo(orderDetailInfo);
            if (null == saveOrderDetailResult || saveOrderDetailResult < 1) {
                log.info("新增订单明细信息失败: orderDetailInfo = " + JSONObject.toJSONString(orderDetailInfo));
                throw new BizException(ResponseEnum.SAVE_ORDER_DETAIL_FAIL, null);
            }
        }
        return ResponseUtil.ok();
    }
    
    @Override
    public void saveOrderInfo(OrderInfo orderInfo) {
        Integer insertResult = orderInfoMapper.insertOrderInfo(orderInfo);
        if (insertResult < 1) {
            log.error("新增订单信息失败: orderInfo = " + JSONObject.toJSONString(orderInfo));
            throw new BizException(ResponseEnum.SAVE_ORDER_FAIL, null);
        }
    }
    
    @Override
    public PageInfo<OrderInfoVo> getOrderInfoVoPage(OrderInfoPageReq orderInfoPageReq) {
        Integer page = orderInfoPageReq.getPage();
        Integer size = orderInfoPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(orderInfoPageReq);
        PageInfo<OrderInfoVo> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            orderInfoMapper.selectOrderInfoByMap(map);
        });
        return pageInfo;
    }
    
    @Override
    public void judgeOrderGoodsPeerNum(Long userId, Long goodsId, BigDecimal peerNum) {
        BigDecimal buyGoodsNum = orderInfoMapper.selectSumGoodsNum(userId, goodsId);
        if (null != buyGoodsNum && buyGoodsNum.compareTo(peerNum) >= 0) {
            log.error("商品可购次数限制,goodsId = " + goodsId + ", peerNum = " + peerNum);
            throw new BizException(ResponseEnum.GOODS_PEER_NUM_LIMIT, null);
        }
    }
    
    @Override
    public OrderInfoVo getOrderInfoVoByOrderNo(String orderNo) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("orderNo", orderNo);
        List<OrderInfoVo> orderInfoVos = orderInfoMapper.selectOrderInfoByMap(map);
        if (null == orderInfoVos || orderInfoVos.size() < 1) {
            return null;
        }
        return orderInfoVos.get(0);
    }
    
    @Override
    public Integer updateOrderStatus(String orderNo, String payOrderNo, String orderStatus, Date updateTime) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUpdateTime(updateTime);
        orderInfo.setOrderNo(orderNo);
        orderInfo.setPayOrderNo(payOrderNo);
        orderInfo.setOrderStatus(orderStatus);
        Integer updateResult = orderInfoMapper.updateOrderStatus(orderInfo);
        if (null == updateResult || updateResult < 1) {
            return 0;
        }
        return updateResult;
    }
    
    @Override
    public OrderInfoVo judgeOrderNoExist(String orderNo) {
        OrderInfoVo orderInfoVo = getOrderInfoVoByOrderNo(orderNo);
        if (null == orderInfoVo) {
            log.error("订单不存在, orderNo = {}", orderNo);
            throw new BizException(ResponseEnum.ORDER_NO_NOT_EXIST, null);
        }
        return orderInfoVo;
    }
    
}
