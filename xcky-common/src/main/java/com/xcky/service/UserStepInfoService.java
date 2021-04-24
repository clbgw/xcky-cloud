package com.xcky.service;

import com.xcky.model.vo.WxRunStepInfoVo;
import java.util.Date;
import java.util.List;

/**
 * 用户步数信息服务接口
 *
 * @author lbchen
 */
public interface UserStepInfoService {
    /**
     * 批量保存用户步数信息
     *
     * @param wxRunStepInfoVos 用户步数信息列表
     * @param openid           用户openid
     * @param nowDate          当前时间
     */
    void batchSaveStepInfos(List<WxRunStepInfoVo> wxRunStepInfoVos, String openid, Date nowDate);
}
