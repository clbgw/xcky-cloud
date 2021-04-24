package com.xcky.convert;

import com.xcky.model.entity.NoticeDistributeConfig;
import com.xcky.model.req.NoticeDistributeConfigUpdateReq;
import org.springframework.beans.BeanUtils;

/**
 * 消息分发配置转换器
 *
 * @author lbchen
 */
public class NoticeDistributeConfigConvert {
    /**
     * 根据消息分发配置更新请求参数获取实体类
     *
     * @param noticeDistributeConfigUpdateReq 消息分发配置更新请求参数
     * @return 消息分发配置实体类
     */
    public static NoticeDistributeConfig getNoticeDistributeConfigByUpdateReq(NoticeDistributeConfigUpdateReq noticeDistributeConfigUpdateReq) {
        if (null == noticeDistributeConfigUpdateReq) {
            return null;
        }
        NoticeDistributeConfig noticeDistributeConfig = new NoticeDistributeConfig();
        BeanUtils.copyProperties(noticeDistributeConfigUpdateReq, noticeDistributeConfig);
        return noticeDistributeConfig;
    }
}
