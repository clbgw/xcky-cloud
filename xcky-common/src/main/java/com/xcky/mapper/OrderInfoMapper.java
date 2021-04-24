package com.xcky.mapper;

import com.xcky.model.entity.OrderInfo;
import com.xcky.model.vo.OrderInfoVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单信息数据访问接口
 *
 * @author lbchen
 */
public interface OrderInfoMapper {
    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 影响行数
     */
    Integer insertOrderInfo(OrderInfo orderInfo);
    
    /**
     * 根据map条件查询订单信息列表
     *
     * @param map map条件
     * @return 订单信息列表
     */
    List<OrderInfoVo> selectOrderInfoByMap(Map<String, Object> map);
    
    /**
     * 查询该用户的订单中,购买此商品的数量
     *
     * @param userId  用户ID
     * @param goodsId 商品ID
     * @return 该用户购买此商品的数量
     */
    BigDecimal selectSumGoodsNum(@Param("userId") Long userId, @Param("goodsId") Long goodsId);
    
    /**
     * 更新订单基本信息
     *
     * @param orderInfo 订单信息
     * @return 更新记录数
     */
    Integer updateOrderStatus(OrderInfo orderInfo);
}
