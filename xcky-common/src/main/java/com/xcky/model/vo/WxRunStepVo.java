package com.xcky.model.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信步数计数值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class WxRunStepVo {
    /**
     * 步数统计列表
     */
    private List<WxRunStepInfoVo> stepInfoList;
    /**
     * 微信水印
     */
    private WxWaterMarkVo watermark;
}
