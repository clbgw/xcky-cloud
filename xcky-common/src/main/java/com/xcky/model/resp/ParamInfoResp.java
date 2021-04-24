package com.xcky.model.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 参数信息响应实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class ParamInfoResp {
    /**
     * 参数值
     */
    private String codeValue;
    /**
     * 参数值描述
     */
    private String codeDesc;
}
