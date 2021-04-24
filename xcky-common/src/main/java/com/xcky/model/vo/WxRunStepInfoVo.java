package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信步数计数信息实体类
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxRunStepInfoVo {
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 步数
     */
    private Integer step;
}
