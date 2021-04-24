package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.GoodsInfoMapper;
import com.xcky.model.req.GoodsDetailReq;
import com.xcky.model.req.GoodsPageReq;
import com.xcky.model.vo.GoodsInfoVo;
import com.xcky.service.GoodsInfoService;
import com.xcky.util.EntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品信息服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class GoodsInfoServiceImpl implements GoodsInfoService {
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;
    
    @Override
    public PageInfo<GoodsInfoVo> getGoodsInfoVoPage(GoodsPageReq goodsPageReq) {
        Integer page = goodsPageReq.getPage();
        Integer size = goodsPageReq.getSize();
        
        Map<String, Object> map = EntityUtil.entityToMap(goodsPageReq);
        log.info(JSONObject.toJSONString(map));
        
        PageInfo<GoodsInfoVo> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            goodsInfoMapper.selectGoodsInfoVos(map);
        });
        return pageInfo;
    }
    
    @Override
    public GoodsInfoVo getGoodsInfoVoDetail(GoodsDetailReq goodsDetailReq) {
        List<GoodsInfoVo> goodsInfoVos = goodsInfoMapper.selectGoodsInfoByReq(goodsDetailReq);
        if(null == goodsInfoVos || goodsInfoVos.size() < 1) {
            return null;
        }
        return goodsInfoVos.get(0);
    }
    
    @Override
    public GoodsInfoVo getGoodsInfoByGoodsId(Long goodsId) {
        GoodsDetailReq goodsDetailReq = new GoodsDetailReq();
        goodsDetailReq.setGoodsId(goodsId);
        return getGoodsInfoVoDetail(goodsDetailReq);
    }
    
    @Override
    public void reduceStock(Long goodsId, BigDecimal num) {
        Integer updateResult = goodsInfoMapper.updateStore(goodsId,num,new Date());
        if(null == updateResult || updateResult < 1) {
            log.error("更新商品库存失败: goodsId = " + goodsId +" , num = " +num);
            throw new BizException(ResponseEnum.UPDATE_ERROR,null);
        }
    }
}
