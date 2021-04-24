package com.xcky.controller;

import com.github.pagehelper.PageInfo;
import com.xcky.model.req.GoodsDetailReq;
import com.xcky.model.req.GoodsPageReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.GoodsInfoVo;
import com.xcky.service.GoodsInfoService;
import com.xcky.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品信息控制器
 *
 * @author lbchen
 */
@RestController
public class GoodsInfoController {
    @Autowired
    private GoodsInfoService goodsInfoService;
    
    /**
     * 获取商品列表
     *
     * @param goodsPageReq 商品分页列表请求参数
     * @return 响应对象
     */
    @PostMapping("/goods/list")
    public R getGoodsInfoList(@RequestBody GoodsPageReq goodsPageReq) {
        PageInfo<GoodsInfoVo> pageInfo = goodsInfoService.getGoodsInfoVoPage(goodsPageReq);
        return ResponseUtil.ok(pageInfo);
    }
    
    /**
     * 获取商品详情
     *
     * @param goodsDetailReq 商品详情请求参数
     * @return 商品详情
     */
    @PostMapping("/goods/detail")
    public R getGoodsInfoDetail(@RequestBody GoodsDetailReq goodsDetailReq) {
        GoodsInfoVo goodsInfoVo = goodsInfoService.getGoodsInfoVoDetail(goodsDetailReq);
        return ResponseUtil.ok(goodsInfoVo);
    }
    
}
