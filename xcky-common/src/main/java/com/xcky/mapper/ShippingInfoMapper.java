package com.xcky.mapper;

import com.xcky.model.entity.ShippingInfo;
import com.xcky.model.req.ShippingInfoDeleteReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收货地址信息数据访问接口
 *
 * @author lbchen
 */
public interface ShippingInfoMapper {
    /**
     * 根据用户ID和收货地址ID查询收货地址信息列表
     *
     * @param userId     用户ID
     * @param shippingId 收货地址ID,可能为空
     * @return 收货地址信息列表
     */
    List<ShippingInfo> selectShippingInfoByKey(@Param("userId") Long userId,
                                               @Param("shippingId") Long shippingId);
    
    /**
     * 新增用户收货地址
     *
     * @param shippingInfo 收货地址信息
     * @return 新增行数
     */
    Integer insertShippingInfo(ShippingInfo shippingInfo);
    
    /**
     * 更新用户收货地址
     *
     * @param shippingInfo 收货地址信息
     * @return 更新行数
     */
    Integer updateShippingInfo(ShippingInfo shippingInfo);
    
    /**
     * 删除收货地址
     *
     * @param shippingInfoDeleteReq 删除收货地址请求参数
     * @return 影响行数
     */
    Integer deleteShippingInfo(ShippingInfoDeleteReq shippingInfoDeleteReq);
}
