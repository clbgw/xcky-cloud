package com.xcky.controller;

import com.github.pagehelper.PageInfo;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.Checkin;
import com.xcky.model.req.CheckinPageReq;
import com.xcky.model.req.CheckinSaveReq;
import com.xcky.model.req.CheckinSubReq;
import com.xcky.model.resp.R;
import com.xcky.service.CheckinService;
import com.xcky.util.DateUtil;
import com.xcky.util.ResponseUtil;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 签到控制器
 *
 * @author lbchen
 */
@RestController
public class CheckinController {
    @Autowired
    private CheckinService checkinService;

    /**
     * 签到
     *
     * @param checkinSaveReq 签到请求参数
     * @return 基本响应对象
     */
    @PostMapping("/checkin/saveCheckin")
    public R saveCheckin(@RequestBody CheckinSaveReq checkinSaveReq) {
        if (null == checkinSaveReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        String appId = checkinSaveReq.getAppid();
        String openid = checkinSaveReq.getOpenid();
        Date checkinTime = checkinSaveReq.getCheckinTime();
        String checkinDate = DateUtil.getTimeStrByDate(checkinTime, DateUtil.DATE_PATTERN);
        // 用户签到
        checkinService.saveCheckinRecord(appId, openid, checkinDate, checkinTime);
        return ResponseUtil.ok();
    }

    /**
     * 订阅明天的签到提醒
     *
     * @param checkinSubReq 订阅明天的签到提醒请求参数
     * @return 基本响应对象
     */
    @PostMapping("/checkin/saveCheckinSub")
    public R saveCheckinSub(@RequestBody CheckinSubReq checkinSubReq) {
        if (null == checkinSubReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        String appid = checkinSubReq.getAppid();
        String openid = checkinSubReq.getOpenid();
        Date subTime = new Date();
        // 用户签到订阅
        checkinService.saveCheckinForTomorrow(appid, openid, subTime);
        return ResponseUtil.ok();
    }

    /**
     * 分页获取签到记录
     *
     * @param checkinPageReq 分页获取签到记录请求参数
     * @return 基本响应对象
     */
    @PostMapping("/checkin/getCheckinRecord")
    public R getCheckinRecord(@RequestBody CheckinPageReq checkinPageReq) {
        if (null == checkinPageReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        // 分页获取签到记录
        PageInfo<Checkin> pageInfo = checkinService.getCheckinByReq(checkinPageReq);
        return ResponseUtil.ok(pageInfo);
    }

}
