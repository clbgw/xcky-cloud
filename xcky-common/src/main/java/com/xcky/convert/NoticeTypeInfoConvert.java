package com.xcky.convert;

import com.xcky.model.entity.NoticeTypeInfo;
import com.xcky.model.req.NoticeTypeUpdateReq;
import org.springframework.beans.BeanUtils;

/**
 * 消息类型信息转换器
 *
 * @author lbchen
 */
public class NoticeTypeInfoConvert {
    /**
     * 根据消息类型更新请求参数获取实体类
     *
     * @param noticeTypeUpdateReq 消息类型更新请求参数
     * @return 消息类型实体类
     */
    public static NoticeTypeInfo getNoticeTypeInfoByUpdateReq(NoticeTypeUpdateReq noticeTypeUpdateReq) {
        if (null == noticeTypeUpdateReq) {
            return null;
        }
        NoticeTypeInfo noticeTypeInfo = new NoticeTypeInfo();
        BeanUtils.copyProperties(noticeTypeUpdateReq, noticeTypeInfo);
        return noticeTypeInfo;
    }
}
