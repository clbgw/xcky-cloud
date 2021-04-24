package com.xcky.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.enums.CheckinSendStatusEnum;
import com.xcky.enums.CheckinStatusEnum;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.mapper.CheckinMapper;
import com.xcky.model.entity.Checkin;
import com.xcky.model.req.CheckinPageReq;
import com.xcky.service.CheckinService;
import com.xcky.util.DateUtil;
import com.xcky.util.EntityUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 签到服务接口实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class CheckinServiceImpl implements CheckinService {
    @Autowired
    private CheckinMapper checkinMapper;

    private Integer updateCheckin(Checkin checkin) {
    	if (null == checkin) {
            return 0;
        }
        boolean isInsert = false;
        String appid = checkin.getAppid();
        String openid = checkin.getOpenid();
        String checkinDate = checkin.getCheckinDate();
        if (StringUtils.isEmpty(appid) || StringUtils.isEmpty(openid) || StringUtils.isEmpty(checkinDate)) {
            isInsert = true;
        } else {
            Checkin checkinQuery = getCheckinByKey(appid, openid, checkinDate);
            if (null == checkinQuery) {
                isInsert = true;
            }
        }
        Date nowDate = new Date();
        checkin.setUpdateTime(nowDate);
        if (isInsert) {
            return checkinMapper.insertCheckin(checkin);
        } else {
            return checkinMapper.updateCheckin(checkin);
        }
    }

    @Override
    public void saveCheckinRecord(String appid, String openid, String checkinDate, Date checkinTime) {
        Checkin checkinQuery = getCheckinByKey(appid, openid, checkinDate);
        if (null == checkinQuery) {
            // 新增签到记录
            Checkin checkin = new Checkin();
            checkin.setAppid(appid);
            checkin.setOpenid(openid);
            checkin.setCheckinDate(checkinDate);
            checkin.setCheckinTime(checkinTime);
            checkin.setSendStatus(CheckinSendStatusEnum.NO_SUB.getCode());
            checkin.setUpdateTime(checkinTime);
            Integer insertResult = checkinMapper.insertCheckin(checkin);
            if (null == insertResult || insertResult < 1) {
                log.error("新增签到记录失败:{}", JSONObject.toJSONString(checkin));
            }
        } else {
            String checkinStatus = checkinQuery.getCheckinStatus();
            if (!CheckinStatusEnum.YES.getCode().equals(checkinStatus)) {
                // 更新为已经签到
                Checkin checkin = new Checkin();
                checkin.setId(checkinQuery.getId());
                checkin.setCheckinTime(checkinTime);
                checkin.setCheckinStatus(CheckinStatusEnum.YES.getCode());
                checkin.setUpdateTime(checkinTime);
                Integer updateResult = checkinMapper.updateCheckin(checkin);
                if (null == updateResult || updateResult < 1) {
                    log.error("更新签到记录失败:{}", JSONObject.toJSONString(checkin));
                }
            } else {
                throw new BizException(ResponseEnum.TODAY_HAD_CHECKIN, null);
            }
        }
    }

    @Override
    public void saveCheckinForTomorrow(String appid, String openid, Date subTime) {
        Checkin checkin = new Checkin();
        checkin.setAppid(appid);
        checkin.setOpenid(openid);
        String checkinDate = DateUtil.getTimeStrByDate(subTime, DateUtil.DATE_PATTERN);
        checkin.setCheckinDate(checkinDate);
        checkin.setCheckinStatus(CheckinStatusEnum.NO.getCode());
        checkin.setSendStatus(CheckinSendStatusEnum.NO_SEND.getCode());
        Integer saveResult = updateCheckin(checkin);
        if (null == saveResult || saveResult < 1) {
            throw new BizException(ResponseEnum.HAD_SUBSCRIPT_CHECKIN, null);
        }
    }

    @Override
    public void updateCheckinSendStatus(Long id, Date sendTime, String sendStatus, String sendErrCode) {
        Checkin checkin = getCheckinById(id);
        if (null == checkin) {
            throw new BizException(ResponseEnum.NO_DATA, null);
        }
        if (null != sendTime) {
            checkin.setSendTime(sendTime);
        }
        if (!StringUtils.isEmpty(sendStatus)) {
            checkin.setSendStatus(sendStatus);
        }
        if (!StringUtils.isEmpty(sendErrCode)) {
            checkin.setSendErrCode(sendErrCode);
        }
        Integer updateResult = updateCheckin(checkin);
        if (null == updateResult || updateResult < 1) {
            throw new BizException(ResponseEnum.UPDATE_ERROR, null);
        }
    }

    @Override
    public Checkin getCheckinByKey(String appid, String openid, String checkinDate) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("appid", appid);
        map.put("openid", openid);
        map.put("checkinDate", checkinDate);
        List<Checkin> checkinList = checkinMapper.selectCheckinByMap(map);
        if (null == checkinList || checkinList.size() < 1) {
            return null;
        }
        return checkinList.get(0);
    }

    @Override
    public Checkin getCheckinById(Long id) {
        Map<String, Object> getKeyMap = new HashMap<>(2);
        getKeyMap.put("id", id);
        List<Checkin> list = checkinMapper.selectCheckinByMap(getKeyMap);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public PageInfo<Checkin> getCheckinByReq(CheckinPageReq checkinPageReq) {
        Integer page = checkinPageReq.getPage();
        Integer size = checkinPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(checkinPageReq);
        PageInfo<Checkin> pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            checkinMapper.selectCheckinByMap(map);
        });
        return pageInfo;
    }

    @Override
    public Integer getCheckinNum(String appid, String openid) {
        return checkinMapper.selectCheckinNum(appid,openid);
    }
}
