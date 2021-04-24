package com.xcky.model.vo;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 购物车值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartVo {
    /**
     * 购物车商品列表
     */
    private List<CartItemVo> cartItems;
    /**
     * 购买总件数
     */
    private Integer totalNum;
    /**
     * 商品总价
     */
    private BigDecimal totalAmount;
    /**
     * 支付总价
     */
    private BigDecimal payAmount;

    /**
     * 优惠总价格
     */
    private BigDecimal couponAmount;


    /**
     * 处理价格
     * @param couponAmount
     */
    public void deealPrice(BigDecimal couponAmount) {
        // 初始化总量和总价
        this.totalNum = 0;
        this.totalAmount = BigDecimal.ZERO;

        // 计算购物项的总量和总价
        if(null != cartItems && cartItems.size() > 0) {
            this.totalNum = cartItems.stream().mapToInt(CartItemVo::getNum).sum();
            for(CartItemVo itemVo: cartItems) {
                this.totalAmount = this.totalAmount.add(itemVo.getTotalPrice());
            }
        }

        // 处理优惠价格
        if(null != couponAmount && couponAmount.compareTo(BigDecimal.ZERO) != 0) {
            this.couponAmount = couponAmount;
            this.payAmount = this.totalAmount.subtract(couponAmount);
        } else {
            this.couponAmount = BigDecimal.ZERO;
            this.payAmount = this.totalAmount;
        }

        // 如果总价小于0,则支付价格为0
        if(null == this.payAmount || this.payAmount.compareTo(BigDecimal.ZERO) < 0) {
            this.payAmount = BigDecimal.ZERO;
        }
    }

}
