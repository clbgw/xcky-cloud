package com.xcky.model.vo;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 购物车商品项值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CartItemVo {
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 商品总价格-计算项(单价*数量)
     */
    private BigDecimal totalPrice;
    /**
     * 商品标题(冗余)
     */
    private String productTitle;
    /**
     * 商品图片(冗余)
     */
    private String productImg;

    /**
     * 获取总价格
     *
     * @return 总价格
     */
    public BigDecimal getTotalPrice() {
        if (0 == num) {
            return BigDecimal.ZERO;
        } else if (1 == num) {
            return this.price;
        }
        return this.price.multiply(new BigDecimal(num));
    }

    /**
     * 设置总价格
     */
    public void setTotalPrice() {
        if (0 == num) {
            this.totalPrice = BigDecimal.ZERO;
        } else if (1 == num) {
            this.totalPrice = this.price;
        } else {
            this.totalPrice = this.price.multiply(new BigDecimal(num));
        }
    }
}
