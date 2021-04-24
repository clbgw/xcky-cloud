package com.xcky.mapper;

import com.xcky.model.entity.GoodsInfo;
import com.xcky.model.req.GoodsDetailReq;
import com.xcky.model.vo.GoodsInfoVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商品信息数据访问接口
 *
 * @author lbchen
 */
public interface GoodsInfoMapper {
    /**
     * 根据主键ID列表查询商品信息列表
     *
     * @param goodsIdList 商品ID列表
     * @return 商品信息列表
     */
    List<GoodsInfo> selectGoodsInfoBySet(@Param("set") Set<Long> goodsIdList);

    /**
     * 扣减商品库存
     *
     * @param goodsId    商品ID
     * @param quantity   扣减商品的数量
     * @param updateTime 更新时间
     * @return 更新行数
     */
    Integer updateStore(@Param("goodsId") Long goodsId, @Param("quantity") BigDecimal quantity, @Param("updateTime") Date updateTime);

    /**
     * 查询商品信息列表
     *
     * @param map 查询列表条件
     * @return 商品信息列表
     */
    List<GoodsInfoVo> selectGoodsInfoVos(Map<String, Object> map);

    /**
     * 查询商品信息详情
     *
     * @param goodsDetailReq
     * @return
     */
    List<GoodsInfoVo> selectGoodsInfoByReq(GoodsDetailReq goodsDetailReq);

    /**
     * 根据商品主键ID查询商品信息
     *
     * @param goodsId 商品ID
     * @return 商品信息值对象
     */
    GoodsInfoVo selectGoodsInfoVoById(@Param("goodsId") Long goodsId);
}
