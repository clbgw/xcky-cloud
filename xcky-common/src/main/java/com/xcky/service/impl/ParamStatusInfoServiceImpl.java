package com.xcky.service.impl;

import com.xcky.mapper.ParamStatusInfoMapper;
import com.xcky.model.entity.ParamStatusInfo;
import com.xcky.service.ParamStatusInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 参数状态信息服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class ParamStatusInfoServiceImpl implements ParamStatusInfoService {
    @Autowired
    private ParamStatusInfoMapper paramStatusInfoMapper;
    @Override
    public ParamStatusInfo getParamStatusInfoByKey(String type, Long userId) {
        // 参数类型预设
        return paramStatusInfoMapper.selectStatusByKey(type, userId);
    }
    
    @Override
    public Integer updateParamStatusInfo(ParamStatusInfo paramStatusInfo) {
        if (null == paramStatusInfo) {
            return 0;
        }
        String type = paramStatusInfo.getType();
        Long userId = paramStatusInfo.getUserId();
        if (StringUtils.isEmpty(type) || null == userId) {
            return 0;
        }
        ParamStatusInfo paramStatusInfoData = getParamStatusInfoByKey(type, userId);
        Date nowDate = new Date();
        Integer updateResult = 0;
        if (null == paramStatusInfoData) {
            // 新增
            paramStatusInfo.setCreateTime(nowDate);
            paramStatusInfo.setUpdateTime(nowDate);
            updateResult = paramStatusInfoMapper.insertParamStatusInfo(paramStatusInfo);
        } else {
            // 更新
            String status = paramStatusInfo.getStatus();
            String statusData = paramStatusInfoData.getStatus();
            if (status.equals(statusData)) {
                // 不用更新数据库，直接返回已更新
                log.info("状态一致,不用更新数据库，直接返回已更新");
                return 2;
            }
            paramStatusInfo.setUpdateTime(nowDate);
            updateResult = paramStatusInfoMapper.updateParamStatusInfo(paramStatusInfo);
        }
        return updateResult;
    }
}
