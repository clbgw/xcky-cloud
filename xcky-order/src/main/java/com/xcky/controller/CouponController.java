package com.xcky.controller;

import com.github.pagehelper.PageInfo;
import com.xcky.enums.ResponseEnum;
import com.xcky.model.entity.CouponInfo;
import com.xcky.model.req.CouponGetReq;
import com.xcky.model.req.CouponPageReq;
import com.xcky.model.resp.R;
import com.xcky.service.CouponService;
import com.xcky.service.UserInfoService;
import com.xcky.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券控制器
 *
 * @author lbchen
 */
@RestController
public class CouponController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户领券列表
     *
     * @param couponPageReq 优惠券分页列表请求参数
     * @return 返回对象
     */
    @PostMapping("/coupon/getPage")
    public R getCouponPage(@RequestBody CouponPageReq couponPageReq) {
        if (null == couponPageReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }
        // 判定用户是否在用户表中存在
        Long userId = couponPageReq.getUserId();
        userInfoService.judgeUserExist(userId);

        // 查询可以领取的优惠券信息列表
        PageInfo<CouponInfo> pageInfo = couponService.getCouponPageByReq(couponPageReq);
        // 返回结果
        return ResponseUtil.ok(pageInfo);
    }


    /**
     * 用户领券
     *
     * @return 返回对象
     */
    @PostMapping("/coupon/get")
    public R getCouponByUser(@RequestBody CouponGetReq couponGetReq) {
        if (null == couponGetReq) {
            return ResponseUtil.fail(ResponseEnum.PARAM_ERROR);
        }

        Long userId = couponGetReq.getUserId();
        Long couponId = couponGetReq.getCouponId();

        // 判定用户是否在用户表中存在
        userInfoService.judgeUserExist(userId);

        // 判断该券是否存在
        couponService.judgeCouponExist(couponId);

        // 领券流程
        couponService.getCouponByReq(couponGetReq);

        // 返回结果
        return ResponseUtil.ok();
    }
}
