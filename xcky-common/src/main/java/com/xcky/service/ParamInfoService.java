package com.xcky.service;

import com.xcky.model.entity.ParamInfo;

import java.util.List;
import java.util.Map;

/**
 * 参数信息服务接口
 *
 * @author lbchen
 */
public interface ParamInfoService {
    /**
     * 根据map查询条件查询参数信息
     *
     * @param map map查询条件
     * @return 参数信息列表
     */
    List<ParamInfo> getParamInfosByMap(Map<String, Object> map);

    /**
     * 判断参数信息是否合法
     *
     * @param classCode 类别编码
     * @param codeValue 编码值
     */
    void judgeParamValid(String classCode, String codeValue);
}
