package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.convert.ShippingInfoConvert;
import com.xcky.mapper.ShippingInfoMapper;
import com.xcky.model.entity.ShippingInfo;
import com.xcky.model.req.ShippingInfoDeleteReq;
import com.xcky.model.req.ShippingInfoPageReq;
import com.xcky.model.req.ShippingInfoUpdateReq;
import com.xcky.service.ShippingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址信息服务实现类
 *
 * @author lbchen
 */
@Service
public class ShippingInfoServiceImpl implements ShippingInfoService {
    @Autowired
    private ShippingInfoMapper shippingInfoMapper;
    
    @Override
    public ShippingInfo getShippingInfoByKey(Long userId, Long shippingId) {
        List<ShippingInfo> list = shippingInfoMapper.selectShippingInfoByKey(userId, shippingId);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    public PageInfo<ShippingInfo> getShippingInfoPageByReq(ShippingInfoPageReq shippingInfoPageReq) {
        Integer page = shippingInfoPageReq.getPage();
        Integer size = shippingInfoPageReq.getSize();
        Long userId = shippingInfoPageReq.getUserId();
        PageInfo<ShippingInfo> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            shippingInfoMapper.selectShippingInfoByKey(userId, null);
        });
        return pageInfo;
    }
    
    @Override
    public Integer updateShippingInfo(ShippingInfoUpdateReq shippingInfoUpdateReq) {
        Long userId = shippingInfoUpdateReq.getUserId();
        Long shippingId = shippingInfoUpdateReq.getShippingId();
        
        ShippingInfo shippingInfo = null;
        boolean isInsert = false;
        if (null == shippingId) {
            isInsert = true;
        } else {
            // 获取用户收货地址信息
            shippingInfo = getShippingInfoByKey(userId, shippingId);
            if (null == shippingInfo) {
                isInsert = true;
            }
        }
        
        Integer updateResult = 0;
        shippingInfo = ShippingInfoConvert.getShippingInfoByUpdateReq(shippingInfoUpdateReq);
        if (isInsert) {
            // 新增用户收货地址
            if(null == shippingInfo) {
                return updateResult;
            }
            updateResult = shippingInfoMapper.insertShippingInfo(shippingInfo);
        } else {
            // 更新用户收货地址
            updateResult = shippingInfoMapper.updateShippingInfo(shippingInfo);
        }
        return updateResult;
    }
    
    @Override
    public Integer deleteShippingInfo(ShippingInfoDeleteReq shippingInfoDeleteReq) {
        return shippingInfoMapper.deleteShippingInfo(shippingInfoDeleteReq);
    }
}
