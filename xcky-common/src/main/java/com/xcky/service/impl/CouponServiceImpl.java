package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.enums.CouponNoStatusEnum;
import com.xcky.enums.CouponStatusEnum;
import com.xcky.enums.CouponUseStatusEnum;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.CouponInfoMapper;
import com.xcky.mapper.CouponNoInfoMapper;
import com.xcky.mapper.UserCouponRelaMapper;
import com.xcky.model.entity.CouponInfo;
import com.xcky.model.entity.CouponNoInfo;
import com.xcky.model.entity.UserCouponRela;
import com.xcky.model.req.CouponGetReq;
import com.xcky.model.req.CouponPageReq;
import com.xcky.model.vo.CouponInfoVo;
import com.xcky.service.CouponService;
import com.xcky.util.EntityUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 优惠券服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponInfoMapper couponInfoMapper;
    @Autowired
    private UserCouponRelaMapper userCouponRelaMapper;
    @Autowired
    private CouponNoInfoMapper couponNoInfoMapper;
    
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void getCouponByReq(CouponGetReq couponGetReq) {
        Date nowDate = new Date();
        Long couponId = couponGetReq.getCouponId();
        Long userId = couponGetReq.getUserId();
        
        // 判断用户领券记录中是否有该类券未使用的
        Integer countCoupon  = userCouponRelaMapper.countNoUseCouponByReq(userId,couponId);
        if(null != countCoupon && countCoupon > 0) {
            log.error("该用户还有该券未使用: couponGetReq = " + JSONObject.toJSONString(couponGetReq));
            throw new BizException(ResponseEnum.HAD_NO_USED_COUPON, null);
        }
        
        // 从优惠券码中选中一个未选用的编码
        String couponNo = pickCouponNo(couponId);
        
        // 创建用户领券实体类
        UserCouponRela userCouponRela = new UserCouponRela();
        userCouponRela.setCreateTime(nowDate);
        userCouponRela.setUpdateTime(nowDate);
        userCouponRela.setUseStatus(CouponUseStatusEnum.COLLECTED.getCode());
        userCouponRela.setUserId(userId);
        userCouponRela.setCouponId(couponId);
        userCouponRela.setCouponNo(couponNo);
        
        // 新增用户领券关系记录
        Integer insertCouponResult = userCouponRelaMapper.insertUserCouponRela(userCouponRela);
        if (null == insertCouponResult || insertCouponResult < 1) {
            log.error("领券失败: couponGetReq = " + JSONObject.toJSONString(couponGetReq));
            throw new BizException(ResponseEnum.GET_COUPON_FAIL, null);
        }
        
        // 扣减优惠券库存
        Long deductionNum = 1L;
        Integer deductionResult = couponInfoMapper.updateCouponNum(couponId,deductionNum);
        if(null == deductionResult || deductionResult < 1) {
            log.error("优惠券扣减库存失败, couponId = " + couponId);
            throw new BizException(ResponseEnum.COUPON_NOT_EXIST, null);
        }
    }
    
    /**
     * 从优惠券码中选中一个未选用的编码
     *
     * @param couponId 优惠券ID
     * @return
     */
    private String pickCouponNo(Long couponId) {
        // 获取一个未使用的优惠券编码
        CouponNoInfo couponNoInfo = couponNoInfoMapper.selectOneCanUseCouponNo(couponId);
        if (null == couponNoInfo) {
            log.error("获取券码: couponId = " + couponId);
            throw new BizException(ResponseEnum.GET_COUPON_NO_FAIL, null);
        }
        // 更新优惠券编码状态
        Date nowDate = new Date();
        Long couponNoInfoId = couponNoInfo.getId();
        String status = CouponNoStatusEnum.USED.getCode();
        Integer updateResult = couponNoInfoMapper.updateCouponNoStatus(couponNoInfoId, status, nowDate);
        
        if (null == updateResult || updateResult < 1) {
            try {
                TimeUnit.MICROSECONDS.sleep(RandomUtils.nextInt(10, 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 重新从券码中挑选一个未选用的编码
            return pickCouponNo(couponId);
        }
        return couponNoInfo.getCouponNo();
    }
    
    @Override
    public void judgeCouponExist(Long couponId) {
        if (null == couponId) {
            log.error("优惠券不存在, couponId 为空");
            throw new BizException(ResponseEnum.COUPON_NOT_EXIST, null);
        }
        // 获取优惠券详情
        Map<String,Object> map = new HashMap<>();
        map.put("couponId",couponId);
        CouponInfo couponInfo = couponInfoMapper.selectCouponByMap(map);
        if (null == couponInfo) {
            log.error("优惠券不存在, couponId = " + couponId);
            throw new BizException(ResponseEnum.COUPON_NOT_EXIST, null);
        }
        
        // 判断库存券是否充足
        Long num = couponInfo.getNum();
        if (null == num || num < 1) {
            log.error("优惠券库存不足, couponId = " + couponId + ", initNum = " + couponInfo.getInitNum());
            throw new BizException(ResponseEnum.COUPON_NOT_EXIST, null);
        }
        
        // 判断优惠券状态是否上架
        String couponStatus = couponInfo.getCouponStatus();
        if (CouponStatusEnum.OFF_SHELF.getCode().equals(couponStatus)) {
            log.error("优惠券已被下架, couponId = " + couponId);
            throw new BizException(ResponseEnum.COUPON_HAD_OFF_SHELF, null);
        } else if (CouponStatusEnum.DELETED.getCode().equals(couponStatus)) {
            log.error("优惠券已被删除, couponId = " + couponId);
            throw new BizException(ResponseEnum.COUPON_HAD_DELETED, null);
        }
        
        // 判断优惠券是否过期
        Date beginDate = couponInfo.getBeginDate();
        Date endDate = couponInfo.getEndDate();
        Date nowDate = new Date();
        if (nowDate.before(beginDate) || nowDate.after(endDate)) {
            log.error("优惠券目前不可领取, couponId = " + couponId);
            throw new BizException(ResponseEnum.COUPON_NOT_GET, null);
        }

    }

    @Override
    public PageInfo<CouponInfo> getCouponPageByReq(CouponPageReq couponPageReq) {
        Integer page = couponPageReq.getPage();
        Integer size = couponPageReq.getSize();
        Map<String,Object> map = EntityUtil.entityToMap(couponPageReq);
        PageInfo<CouponInfo> pageInfo = PageHelper.startPage(page,size).doSelectPageInfo(()->{
            couponInfoMapper.selectCouponByMap(map);
        });
        return pageInfo;
    }
}
