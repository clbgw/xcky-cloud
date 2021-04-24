package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.CouponInfo;
import com.xcky.model.req.CouponGetReq;
import com.xcky.model.req.CouponPageReq;

/**
 * 优惠券服务接口
 *
 * @author lbchen
 */
public interface CouponService {
    /**
     * 用户领券
     *
     * @param couponGetReq 用户领券请求参数
     */
    void getCouponByReq(CouponGetReq couponGetReq);

    /**
     * 判定优惠券是否存在
     *
     * @param couponId 优惠券ID
     */
    void judgeCouponExist(Long couponId);

    /**
     * 根据条件分页查询优惠券信息列表
     *
     * @param couponPageReq 优惠券分页请求参数
     * @return 优惠券信息分页列表
     */
    PageInfo<CouponInfo> getCouponPageByReq(CouponPageReq couponPageReq);
}
