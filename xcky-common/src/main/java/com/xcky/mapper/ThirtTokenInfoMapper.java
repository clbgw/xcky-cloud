package com.xcky.mapper;

import com.xcky.model.entity.ThirtTokenInfo;

import java.util.List;
import java.util.Map;

/**
 * 第三方令牌信息对象映射接口
 *
 * @author lbchen
 */
public interface ThirtTokenInfoMapper {
    /**
     * 新增第三方会话信息
     *
     * @param thirtTokenInfo 第三方会话信息
     * @return 新增行数
     */
    Integer insertThirtTokenInfo(ThirtTokenInfo thirtTokenInfo);
    
    /**
     * 更新第三方会话信息
     *
     * @param thirtTokenInfo 第三方会话信息
     * @return 更新行数
     */
    Integer updateThirtTokenInfo(ThirtTokenInfo thirtTokenInfo);
    
    /**
     * 根据map条件查询第三方会话信息
     *
     * @param map map条件
     * @return 第三方会话信息列表
     */
    List<ThirtTokenInfo> selectThirtTokenInfosByMap(Map<String, Object> map);
    
}
