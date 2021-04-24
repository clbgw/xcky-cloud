package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.ShippingInfo;
import com.xcky.model.req.ShippingInfoDeleteReq;
import com.xcky.model.req.ShippingInfoPageReq;
import com.xcky.model.req.ShippingInfoUpdateReq;

/**
 * 收货地址信息服务接口
 *
 * @author lbchen
 */
public interface ShippingInfoService {
    /**
     * 根据收货地址主键获取收货地址
     *
     * @param userId     用户ID
     * @param shippingId 收货地址ID
     * @return 收货地址信息
     */
    ShippingInfo getShippingInfoByKey(Long userId, Long shippingId);
    
    /**
     * 根据分页请求参数获取收货地址分页信息
     *
     * @param shippingInfoPageReq 收货地址分页请求对象
     * @return 收货地址分页信息
     */
    PageInfo<ShippingInfo> getShippingInfoPageByReq(ShippingInfoPageReq shippingInfoPageReq);
    
    /**
     * 新增或更新收货地址
     *
     * @param shippingInfoUpdateReq 收货地址更新请求参数
     * @return
     */
    Integer updateShippingInfo(ShippingInfoUpdateReq shippingInfoUpdateReq);
    
    /**
     * 删除收货地址
     * @param shippingInfoDeleteReq 删除收货地址请求参数
     * @return
     */
    Integer deleteShippingInfo(ShippingInfoDeleteReq shippingInfoDeleteReq);
}
