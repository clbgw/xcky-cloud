package com.xcky.mapper;

import com.xcky.model.entity.CouponInfo;
import com.xcky.model.vo.CouponInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 优惠券信息数据访问接口
 *
 * @author lbchen
 */
public interface CouponInfoMapper {
    /**
     * 查询用户优惠券列表
     *
     * @param map map条件
     * @return 优惠券列表
     */
    List<CouponInfoVo> selectUserCouponByMap(Map<String, Object> map);
    
    /**
     * 获取优惠券列表
     *
     * @param map 优惠券列表
     * @return 优惠券列表信息
     */
    CouponInfo selectCouponByMap(Map<String,Object> map);
    
    /**
     * 扣减优惠券库存
     *
     * @param couponId     优惠券ID
     * @param deductionNum 扣减库存数量
     * @return 更新行数
     */
    Integer updateCouponNum(@Param("couponId") Long couponId, @Param("deductionNum") Long deductionNum);
}
