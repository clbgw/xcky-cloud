package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.log.Log;
import com.xcky.mapper.GoodsInfoMapper;
import com.xcky.model.entity.GoodsInfo;
import com.xcky.model.req.CartChangeReq;
import com.xcky.model.req.CartDeleteItemReq;
import com.xcky.model.req.CartSaveItemReq;
import com.xcky.model.vo.CartItemVo;
import com.xcky.model.vo.CartVo;
import com.xcky.model.vo.GoodsInfoVo;
import com.xcky.service.CartService;
import com.xcky.util.Constants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 购物车服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Override
    public void saveToCard(Long userId, List<CartSaveItemReq> cartSaveItemReqs) {
        String cartKey = getCartKey(userId);
        BoundHashOperations<String, Long, String> bonudHashOps = redisTemplate.boundHashOps(cartKey);
        Long productId;
        GoodsInfoVo goodsInfoVo;
        CartItemVo cartItemVo;
        for (CartSaveItemReq cartSaveItemReq : cartSaveItemReqs) {
            if (null == cartSaveItemReq || null == cartSaveItemReq.getProductId()) {
                continue;
            }
            updateCartItem(bonudHashOps, cartSaveItemReq.getProductId(), cartSaveItemReq.getNum(), true);
        }
    }

    @Override
    public void deleteCard(Long userId) {
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    @Override
    public void deleteCardItem(CartDeleteItemReq cartDeleteItemReq) {
        String cartKey = getCartKey(cartDeleteItemReq.getUserId());
        BoundHashOperations<String, Long, String> bonudHashOps = redisTemplate.boundHashOps(cartKey);
        List<Long> productIds = cartDeleteItemReq.getProductIds();
        if (null != productIds && productIds.size() > 0) {
            for (Long productId : productIds) {
                // 注意此处的入参不能够是list数组直接入参
                bonudHashOps.delete(productId);
            }
        }
    }

    @Override
    public void changeCartItemNum(CartChangeReq cartChangeReq) {
        String cartKey = getCartKey(cartChangeReq.getUserId());
        BoundHashOperations<String, Long, String> bonudHashOps = redisTemplate.boundHashOps(cartKey);
        updateCartItem(bonudHashOps, cartChangeReq.getProductId(), cartChangeReq.getNum(), false);
    }

    @Override
    public CartVo getCartByUserId(Long userId, boolean isSync) {
        String cartKey = getCartKey(userId);
        BoundHashOperations<String, Long, String> bonudHashOps = redisTemplate.boundHashOps(cartKey);
        List<String> cartItemVoStrList = bonudHashOps.values();
        if (null == cartItemVoStrList || cartItemVoStrList.size() < 1) {
            return null;
        }

        List<CartItemVo> cartItemVos;
        CartItemVo cartItemVo;
        CartVo cartVo = new CartVo();
        cartItemVos = new ArrayList<>();
        // 如果需要同步价格
        if (isSync) {
            Set<Long> productIds = new HashSet<>();
            for (String cartItemStr : cartItemVoStrList) {
                cartItemVo = JSONObject.parseObject(cartItemStr, CartItemVo.class);
                productIds.add(cartItemVo.getProductId());
                cartItemVos.add(cartItemVo);
            }
            // 根据商品ID列表查询商品列表
            List<GoodsInfo> goodsInfos = goodsInfoMapper.selectGoodsInfoBySet(productIds);
            Map<Long, GoodsInfo> goodsInfoMap = new HashMap<>();
            for (GoodsInfo goodsInfo : goodsInfos) {
                goodsInfoMap.put(goodsInfo.getId(), goodsInfo);
            }

            List<CartItemVo> cartItemVoTemps = new ArrayList<>();
            CartItemVo itemTemp;
            for (CartItemVo item : cartItemVos) {
                itemTemp = goodsToCartItem(goodsInfoMap.get(item.getProductId()), item.getNum());
                cartItemVoTemps.add(itemTemp);
            }
            cartVo.setCartItems(cartItemVoTemps);
            cartVo.deealPrice(BigDecimal.ZERO);
            return cartVo;
        } else {
            for (String cartItemStr : cartItemVoStrList) {
                cartItemVo = JSONObject.parseObject(cartItemStr, CartItemVo.class);
                cartItemVos.add(cartItemVo);
            }
            cartVo.setCartItems(cartItemVos);
            cartVo.deealPrice(BigDecimal.ZERO);
            return cartVo;
        }
    }

    /**
     * 商品信息转化为购物车商品项信息
     *
     * @param item 商品信息
     * @param num  商品数量
     * @return 购物车商品项信息
     */
    private CartItemVo goodsToCartItem(GoodsInfo item, Integer num) {
        CartItemVo cartItemVo = new CartItemVo();
        cartItemVo.setNum(num);
        cartItemVo.setPrice(item.getPrice());
        cartItemVo.setProductId(item.getId());
        cartItemVo.setProductImg(item.getMainImg());
        cartItemVo.setProductTitle(item.getGoodsName());
        cartItemVo.setTotalPrice(BigDecimal.ZERO);
        return cartItemVo;
    }

    /**
     * 修改购物车子项数据
     *
     * @param bonudHashOps redis的hash操作
     * @param productId    商品ID
     * @param num          商品数量
     * @param isSave       是否新增
     */
    private void updateCartItem(BoundHashOperations<String, Long, String> bonudHashOps, Long productId, Integer num, boolean isSave) {
        String cartItemVoStr = bonudHashOps.get(productId);
        CartItemVo cartItemVo;
        if (StringUtils.isEmpty(cartItemVoStr)) {
            // 查询商品详情
            GoodsInfoVo goodsInfoVo = goodsInfoMapper.selectGoodsInfoVoById(productId);
            if (null != goodsInfoVo) {
                // 保存购物车的商品项数据
                cartItemVo = new CartItemVo();
                cartItemVo.setNum(num);
                cartItemVo.setPrice(goodsInfoVo.getPrice());
                cartItemVo.setProductId(goodsInfoVo.getId());
                cartItemVo.setProductImg(goodsInfoVo.getMainImg());
                cartItemVo.setProductTitle(goodsInfoVo.getGoodsName());
                cartItemVo.setTotalPrice(BigDecimal.ZERO);
                bonudHashOps.put(productId, JSONObject.toJSONString(cartItemVo));
            }
        } else {
            // 修改购物车的商品项数据
            cartItemVo = JSONObject.parseObject(cartItemVoStr, CartItemVo.class);
            if (isSave) {
                cartItemVo.setNum(cartItemVo.getNum() + num);
            } else {
                cartItemVo.setNum(num);
            }
            cartItemVo.setTotalPrice(BigDecimal.ZERO);
            bonudHashOps.put(productId, JSONObject.toJSONString(cartItemVo));
        }
    }

    /**
     * 获取用户购物车的KEY
     *
     * @param userId 用户ID
     * @return 用户购物车的KEY
     */
    private String getCartKey(Long userId) {
        return Constants.CART_KEY_PREFIX + userId;
    }
}
