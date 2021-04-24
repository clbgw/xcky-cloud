package com.xcky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xcky.mapper.UserStepInfoMapper;
import com.xcky.model.entity.UserStepInfo;
import com.xcky.model.vo.WxRunStepInfoVo;
import com.xcky.service.UserStepInfoService;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 用户步数信息服务实现类
 *
 * @author lbchen
 */
@Slf4j
@Service
public class UserStepInfoServiceImpl implements UserStepInfoService {
    @Autowired
    private UserStepInfoMapper userStepInfoMapper;

    @Override
    public void batchSaveStepInfos(List<WxRunStepInfoVo> wxRunStepInfoVos, String openid, Date nowDate) {
        UserStepInfo userStepInfo;
        for (WxRunStepInfoVo wxRunStepInfoVo : wxRunStepInfoVos) {
            userStepInfo = new UserStepInfo();
            userStepInfo.setCreateTime(nowDate);
            userStepInfo.setOpenid(openid);
            userStepInfo.setStep(wxRunStepInfoVo.getStep());
            Date stepDate = new Date(wxRunStepInfoVo.getTimestamp() * 1000L);
            userStepInfo.setStepDate(stepDate);
            // 根据openid 和步数日期查询数据库是否存在,如果存在则更新,不存在则保存
            List<UserStepInfo> userStepInfos = userStepInfoMapper.selectUserStepOne(stepDate, openid);
            if (null == userStepInfos || userStepInfos.size() < 1) {
                // 新增
                Integer insertResult = userStepInfoMapper.insertUserStepInfo(userStepInfo);
                if (null == insertResult || insertResult < 1) {
                    log.error("新增用户步数失败:" + JSONObject.toJSONString(userStepInfo));
                }
            } else {
                // 更新
                Integer updateResult = userStepInfoMapper.updateUserStepInfo(userStepInfo);
                if (null == updateResult || updateResult < 1) {
                    log.error("更新用户步数失败:" + JSONObject.toJSONString(userStepInfo));
                }
            }
        }
    }
}
