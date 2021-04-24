package com.xcky.model.wx;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxGetAccessTokenResp extends WxBaseResp {
    /**
     * 凭证
     */
    @JSONField(name = "access_token")
    private String accessToken;
    /**
     * 凭证有效时间，单位：秒。目前是7200秒之内的值。
     */
    @JSONField(name = "expires_in")
    private Integer expiresIn;
}
