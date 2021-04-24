package com.xcky.model.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信水印值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxWaterMarkVo {
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 公众号appid
     */
    private String appid;
}
