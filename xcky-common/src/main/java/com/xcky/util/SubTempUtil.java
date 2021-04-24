package com.xcky.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 订阅模板工具类
 *
 * @author lbchen
 */
@Component
public class SubTempUtil {

    /**
     * 获取模板消息要求内容数据
     *
     * @param config   配置节点数据
     * @param openid   用户
     * @param paramMap 参数MAP
     * @return 模板消息map
     */
    public Map<String, Object> getSubData(Map<String, Object> config, String openid,
                                          Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Object dataObj = config.get("data");
        String jsonStr = JSONObject.toJSONString(dataObj);
        Map<String, Object> dataMap = JSONObject.parseObject(jsonStr).getInnerMap();
        Map<String, Map<String, Object>> valueMap = new HashMap<>(8);
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String value = "" + entry.getValue();
            Map<String, Object> tempMap = new HashMap<>(2);
            if ("num".equals(value)) {
                tempMap.put("value", paramMap.get("num"));
            } else if ("nowDate".equals(value)) {
                tempMap.put("value", paramMap.get("nowDate"));
            } else if ("nowTime".equals(value)) {
                tempMap.put("value", paramMap.get("nowTime"));
            } else if ("nickName".equals(value)) {
                tempMap.put("value", paramMap.get("nickName"));
            } else {
                tempMap.put("value", value);
            }
            valueMap.put(key, tempMap);
        }
        resultMap.put("data", valueMap);
        resultMap.put("touser", openid);
        resultMap.put("template_id", "" + config.get("template_id"));
        resultMap.put("page", "" + config.get("page"));
        resultMap.put("miniprogram_state", "" + config.get("miniprogram_state"));
        resultMap.put("lang", "" + config.get("lang"));
        return resultMap;
    }
}
