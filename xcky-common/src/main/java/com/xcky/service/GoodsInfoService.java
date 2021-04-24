package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.req.GoodsDetailReq;
import com.xcky.model.req.GoodsPageReq;
import com.xcky.model.vo.GoodsInfoVo;

import java.math.BigDecimal;

/**
 * 商品信息服务接口
 *
 * @author lbchen
 */
public interface GoodsInfoService {
    /**
     * 获取商品信息分页列表
     *
     * @param goodsPageReq 商品信息分页列表请求参数
     * @return 商品信息分页列表
     */
    PageInfo<GoodsInfoVo> getGoodsInfoVoPage(GoodsPageReq goodsPageReq);
    
    /**
     * 获取商品详情
     *
     * @param goodsDetailReq 商品详情请求参数
     * @return 商品详情
     */
    GoodsInfoVo getGoodsInfoVoDetail(GoodsDetailReq goodsDetailReq);
    
    /**
     * 根据商品编号获取商品信息
     *
     * @param goodsId 商品编号
     * @return 商品信息
     */
    GoodsInfoVo getGoodsInfoByGoodsId(Long goodsId);
    
    /**
     * 扣减库存
     *
     * @param goodsId 奖品ID
     * @param num     扣除奖品数量
     */
    void reduceStock(Long goodsId, BigDecimal num);
}
