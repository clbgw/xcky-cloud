package com.xcky.mapper;

import com.xcky.model.entity.CouponNoInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 优惠券编码数据访问接口
 *
 * @author lbchen
 */
public interface CouponNoInfoMapper {
    /**
     * 从券码中选择一个未被领用的优惠券
     *
     * @param couponId 优惠券ID
     * @return 券码
     */
    CouponNoInfo selectOneCanUseCouponNo(@Param("couponId") Long couponId);
    
    /**
     * 更新优惠券券码状态
     *
     * @param id         主键ID
     * @param status     状态
     * @param updateTime 更新时间
     * @return 影响行数
     */
    Integer updateCouponNoStatus(@Param("id") Long id,
                                 @Param("status") String status,
                                 @Param("updateTime") Date updateTime);
}
