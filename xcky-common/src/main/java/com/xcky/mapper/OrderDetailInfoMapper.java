package com.xcky.mapper;

import com.xcky.model.entity.OrderDetailInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单明细信息数据访问接口
 *
 * @author lbchen
 */
public interface OrderDetailInfoMapper {
    /**
     * 新增订单明细信息
     *
     * @param orderDetailInfo 订单详情信息
     * @return 影响行数
     */
    Integer insertOrderDetailInfo(OrderDetailInfo orderDetailInfo);
    
    /**
     * 根据订单编号查询订单详细信息列表
     *
     * @param orderNo 订单编号
     * @return 订单详细信息列表
     */
    List<OrderDetailInfo> selectOrderDetailInfosByOrderNo(@Param("orderNo") String orderNo);
}
