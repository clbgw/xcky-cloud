package com.xcky.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.ParamInfoMapper;
import com.xcky.model.entity.ParamInfo;
import com.xcky.service.ParamInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 参数信息服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class ParamInfoServiceImpl implements ParamInfoService {
    @Autowired
    private ParamInfoMapper paramInfoMapper;

    @Override
    public List<ParamInfo> getParamInfosByMap(Map<String, Object> map) {
        return paramInfoMapper.selectParamInfosByMap(map);
    }

    @Override
    public void judgeParamValid(String classCode, String codeValue) {
        Map<String, Object> map = new HashMap<>(4);
        List<ParamInfo> list = paramInfoMapper.selectParamInfosByMap(map);
        if (null == list || list.size() < 1) {
            log.error("您还未在参数表中设置参数，classCode = {}, codeValue = {}", classCode, codeValue);
            throw new BizException(ResponseEnum.PARAM_STATUS_TYPE_ERROR, null);
        }
    }
}
