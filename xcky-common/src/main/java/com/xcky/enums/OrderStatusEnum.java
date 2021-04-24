package com.xcky.enums;

import lombok.Getter;

/**
 * 订单状态枚举接口<br>
 * <ul>
 *     <li>0-已成功</li>
 *     <li>1-待付款</li>
 *     <li>2-已取消</li>
 *     <li>3-已退款</li>
 *     <li>66-调用订单接口异常</li>
 * </ul>
 *
 * @author lbchen
 */
@Getter
public enum OrderStatusEnum {
    /**
     * 已成功
     */
    SUCCESS("0", "已成功"),
    /**
     * 待付款
     */
    WAIT("1", "待付款"),
    /**
     * 已取消
     */
    CANCEL("2", "已取消"),
    /**
     * 已退款
     */
    REFUND("3", "已退款"),
    /**
     * 调用订单接口异常
     */
    DEAL_FAIL("66","调用订单接口异常")
    ;
    
    /**
     * 订单状态编码
     */
    private final String code;
    /**
     * 订单状态描述
     */
    private final String desc;
    
    OrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
}
