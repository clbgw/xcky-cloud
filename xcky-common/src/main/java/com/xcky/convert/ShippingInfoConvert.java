package com.xcky.convert;

import com.xcky.model.entity.ShippingInfo;
import com.xcky.model.req.ShippingInfoUpdateReq;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 收货地址信息转化类
 *
 * @author lbchen
 */
public class ShippingInfoConvert {
    /**
     * 通过更新请求对象转化成实体类
     *
     * @param shippingInfoUpdateReq 收货地址信息更新请求对象
     * @return 收货地址实体类
     */
    public static ShippingInfo getShippingInfoByUpdateReq(ShippingInfoUpdateReq shippingInfoUpdateReq) {
        if (null == shippingInfoUpdateReq) {
            return null;
        }
        ShippingInfo shippingInfo = new ShippingInfo();
        Date nowDate = new Date();
        BeanUtils.copyProperties(shippingInfoUpdateReq, shippingInfo);
        shippingInfo.setCreateTime(nowDate);
        shippingInfo.setUpdateTime(nowDate);
        Long id = shippingInfoUpdateReq.getShippingId();
        if (null != id) {
            shippingInfo.setId(id);
        }
        return shippingInfo;
    }
}
