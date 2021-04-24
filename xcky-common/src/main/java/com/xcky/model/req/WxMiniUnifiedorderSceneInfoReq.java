package com.xcky.model.req;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信小程序支付统一下单中的场景信息请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
public class WxMiniUnifiedorderSceneInfoReq {
    /******* 必填字段 *********/
    /**
     * 门店id
     */
    private String id;
    /******* 选填字段 *********/
    /**
     * 门店名称
     */
    private String name;
    /**
     * 门店行政区划码
     */
    @JSONField(name = "area_code")
    private String areaCode;
    /**
     * 门店详细地址
     */
    private String address;
}
