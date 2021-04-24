package com.xcky.service;

import com.xcky.model.entity.ParamStatusInfo;

/**
 * 参数状态信息服务接口
 *
 * @author lbchen
 */
public interface ParamStatusInfoService {
    /**
     * 根据主键查询参数状态信息
     *
     * @param type   参数类型
     * @param userId 用户ID
     * @return 参数状态信息
     */
    ParamStatusInfo getParamStatusInfoByKey(String type, Long userId);
    
    /**
     * 更新参数状态信息
     *
     * @param paramStatusInfo 参数状态信息
     * @return 影响行数
     */
    Integer updateParamStatusInfo(ParamStatusInfo paramStatusInfo);
}
