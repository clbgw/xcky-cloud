package com.xcky.convert;

import com.xcky.model.entity.NoticeInfo;
import com.xcky.model.req.NoticeInfoUpdateReq;
import org.springframework.beans.BeanUtils;

/**
 * 消息信息转换器
 *
 * @author lbchen
 */
public class NoticeInfoConvert {
    /**
     * 根据消息信息更新请求参数获取消息信息实体类
     *
     * @param noticeInfoUpdateReq 消息信息更新请求参数
     * @return 消息信息实体类
     */
    public static NoticeInfo getNoticeInfoByUpdateReq(NoticeInfoUpdateReq noticeInfoUpdateReq) {
        if (null == noticeInfoUpdateReq) {
            return null;
        }
        NoticeInfo noticeInfo = new NoticeInfo();
        BeanUtils.copyProperties(noticeInfoUpdateReq, noticeInfo);
        return noticeInfo;
    }
}
