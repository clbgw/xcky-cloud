package com.xcky.mapper;

import com.xcky.model.entity.UserCouponRela;
import org.apache.ibatis.annotations.Param;

/**
 * 用户领券关系数据访问接口
 *
 * @author lbchen
 */
public interface UserCouponRelaMapper {
    /**
     * 更新用户领券关系表状态
     *
     * @param userCouponRela 用户领券关系对象
     * @return 影响行数
     */
    Integer updateUserCouponStatus(UserCouponRela userCouponRela);
    
    /**
     * 新增用户领券关系
     *
     * @param userCouponRela 用户领券对象
     * @return 影响行数
     */
    Integer insertUserCouponRela(UserCouponRela userCouponRela);
    
    /**
     * 统计用户是否有该类未使用的券
     *
     * @param userId   用户编号
     * @param couponId 券编号
     * @return
     */
    Integer countNoUseCouponByReq(@Param("userId") Long userId,
                                  @Param("couponId") Long couponId);
}
