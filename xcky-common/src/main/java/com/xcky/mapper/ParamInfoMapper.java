package com.xcky.mapper;

import com.xcky.model.entity.ParamInfo;

import java.util.List;
import java.util.Map;

/**
 * 参数信息对象映射接口
 *
 * @author lbchen
 */
public interface ParamInfoMapper {
    /**
     * 根据map查询条件查询参数信息
     *
     * @param map map查询条件
     * @return 参数信息列表
     */
    List<ParamInfo> selectParamInfosByMap(Map<String, Object> map);
}
