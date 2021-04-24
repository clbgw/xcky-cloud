package com.xcky.schedule;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xcky.enums.NoticeDistributeInfoStatusEnum;
import com.xcky.model.entity.NoticeDistributeInfo;
import com.xcky.model.req.NoticeDistributeConfigRespPageReq;
import com.xcky.model.resp.NoticeDistributeConfigResp;
import com.xcky.service.NoticeDistributeConfigService;
import com.xcky.service.NoticeDistributeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lbchen
 */
@Component
@Slf4j
public class NoticeJob {
    @Autowired
    private NoticeDistributeInfoService noticeDistributeInfoService;
    @Autowired
    private NoticeDistributeConfigService noticeDistributeConfigService;
    /**
     * 扫描订单
     */
    @Scheduled(cron = "10 1 * * * *")
    public void orderNoticeScan() {
        //TODO
        log.info("orderNoticeScan");
        Integer startPage = 1;
        Integer size = 10;
        NoticeDistributeConfigRespPageReq noticeDistributeConfigRespPageReq = new NoticeDistributeConfigRespPageReq();
        boolean isLoop = true;
        Date nowDate = new Date();
        while (isLoop) {
            noticeDistributeConfigRespPageReq.setPage(startPage);
            noticeDistributeConfigRespPageReq.setSize(size);
            PageInfo<NoticeDistributeConfigResp> pageInfo = noticeDistributeConfigService.getRespPageInfo(noticeDistributeConfigRespPageReq);
            if(null == pageInfo || null == pageInfo.getList() || pageInfo.getList().size() < 1) {
                break;
            }
            List<NoticeDistributeConfigResp> noticeDistributeConfigResps = pageInfo.getList();
            NoticeDistributeInfo noticeDistributeInfo;
            for(NoticeDistributeConfigResp noticeDistributeConfigResp : noticeDistributeConfigResps) {
                Long noticeId = noticeDistributeConfigResp.getNoticeId();
                if(null == noticeDistributeConfigResp || null == noticeId) {
                    continue;
                }
                Map<String,Object> detailMap = new HashMap<>(8);
                noticeDistributeInfo = new NoticeDistributeInfo();
                Integer roleId = noticeDistributeConfigResp.getNoticeRoleId();
                if(null != roleId) {
                    noticeDistributeInfo.setRoleId(roleId);
                    detailMap.put("roleId", roleId);
                }
                Integer userId = noticeDistributeConfigResp.getNoticeUserId();
                if(null != userId) {
                    noticeDistributeInfo.setUserId(userId);
                    detailMap.put("userId", userId);
                }
                noticeDistributeInfo.setNoticeId(noticeId);
                noticeDistributeInfo.setCreateTime(nowDate);
                noticeDistributeInfo.setUpdateTime(nowDate);
                String noticeDistributeInfoStatus = NoticeDistributeInfoStatusEnum.NO_READ.getCode();
                noticeDistributeInfo.setStatus(noticeDistributeInfoStatus);
                //TODO
                noticeDistributeInfo.setMsgContent(noticeDistributeConfigResp.getMessageTemplate()
                        +"\n用户("+noticeDistributeConfigResp.getUserName()+")"
                        +"\n角色("+noticeDistributeConfigResp.getRoleName()+")"
                );
                
                detailMap.put("noticeId", noticeId);
                detailMap.put("status", noticeDistributeInfoStatus);
                NoticeDistributeInfo noticeDistributeInfoTemp = noticeDistributeInfoService.getDetailByMap(detailMap);
                if(null != noticeDistributeInfoTemp) {
                    log.info("内容已存在:" + JSONObject.toJSONString(detailMap));
                    continue;
                }
                Integer insertResult = noticeDistributeInfoService.updateNoticeDistributeInfo(noticeDistributeInfo);
                if(null == insertResult || insertResult < 1) {
                    log.error("新增消息分发内容失败: " + JSONObject.toJSONString(noticeDistributeInfo));
                }
            }
            startPage ++;
        }
    }
}
