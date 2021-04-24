package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信小程序二维码新增记录请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlQrcodeCreateReq {
    /**
     * 微信APPID
     */
    private String appid;
    /**
     * 简历ID
     */
    private Long jlId;
    /**
     * 是否只是查询，1-查询，2-更新
     */
    private Integer isQuery = 1;
}
