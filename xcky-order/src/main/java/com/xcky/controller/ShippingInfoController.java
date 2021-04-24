package com.xcky.controller;

import com.github.pagehelper.PageInfo;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.ShippingInfo;
import com.xcky.model.req.ShippingInfoDeleteReq;
import com.xcky.model.req.ShippingInfoDetailReq;
import com.xcky.model.req.ShippingInfoPageReq;
import com.xcky.model.req.ShippingInfoUpdateReq;
import com.xcky.model.resp.R;
import com.xcky.service.ShippingInfoService;
import com.xcky.service.UserInfoService;
import com.xcky.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收货地址信息控制器
 * @author lbchen
 */
@RestController
@Slf4j
public class ShippingInfoController {
    @Autowired
    private ShippingInfoService shippingInfoService;
    @Autowired
    private UserInfoService userInfoService;
    
    /**
     * 查询收货地址分页信息
     *
     * @param shippingInfoPageReq 收货地址分页列表请求参数
     * @return 响应对象
     */
    @GetMapping("/shipping/list")
    public R getShippingInfoPage(ShippingInfoPageReq shippingInfoPageReq) {
        if (null == shippingInfoPageReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        // 判断用户是否存在
        userInfoService.judgeUserExist(shippingInfoPageReq.getUserId());
        // 分页查询用户的收货地址信息
        PageInfo<ShippingInfo> pageInfo = shippingInfoService.getShippingInfoPageByReq(shippingInfoPageReq);
        return ResponseUtil.ok(pageInfo);
    }
    
    /**
     * 获取收货地址详情
     *
     * @param shippingInfoDetailReq 收货地址详情请求参数
     * @return 响应对象
     */
    @GetMapping("/shipping/detail")
    public R getShippingInfoDetail(ShippingInfoDetailReq shippingInfoDetailReq) {
        if (null == shippingInfoDetailReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        // 判断用户是否存在
        Long userId = shippingInfoDetailReq.getUserId();
        userInfoService.judgeUserExist(userId);
        
        Long shippingId = shippingInfoDetailReq.getShippingId();
        if (null == shippingId) {
            log.error("未选择收货地址");
            throw new BizException(ResponseEnum.NO_SELECTED_SHIPPING, null);
        }
        // 查询收货地址详细信息
        ShippingInfo shippingInfo = shippingInfoService.getShippingInfoByKey(userId, shippingId);
        // 暂无数据
        if (null == shippingInfo) {
            log.error("暂无数据, userId = " + userId + " , shippingId = " + shippingId);
            throw new BizException(ResponseEnum.NO_DATA, null);
        }
        return ResponseUtil.ok(shippingInfo);
    }
    
    /**
     * 更新收货地址
     *
     * @param shippingInfoUpdateReq 更新收货地址请求参数
     * @return 响应对象
     */
    @PostMapping("/shipping/update")
    public R updateShippingInfo(@RequestBody ShippingInfoUpdateReq shippingInfoUpdateReq) {
        if (null == shippingInfoUpdateReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        // 判断用户是否存在
        Long userId = shippingInfoUpdateReq.getUserId();
        userInfoService.judgeUserExist(userId);
        Integer updateResult = shippingInfoService.updateShippingInfo(shippingInfoUpdateReq);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok();
    }
    
    /**
     * 删除收货地址
     *
     * @param shippingInfoDeleteReq 更新收货地址请求参数
     * @return 响应对象
     */
    @PostMapping("/shipping/delete")
    public R deleteShippingInfo(@RequestBody ShippingInfoDeleteReq shippingInfoDeleteReq) {
        if (null == shippingInfoDeleteReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        // 判断用户是否存在
        Long userId = shippingInfoDeleteReq.getUserId();
        userInfoService.judgeUserExist(userId);
        Integer deleteResult = shippingInfoService.deleteShippingInfo(shippingInfoDeleteReq);
        if (null == deleteResult || deleteResult < 1) {
            return ResponseUtil.fail(ResponseEnum.DELETE_ERROR);
        }
        return ResponseUtil.ok();
    }
}
