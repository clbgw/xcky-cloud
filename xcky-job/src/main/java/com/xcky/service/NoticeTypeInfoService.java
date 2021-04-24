package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.NoticeTypeInfo;
import com.xcky.model.req.NoticeTypePageReq;
import java.util.List;

/**
 * 消息类型服务接口
 *
 * @author lbchen
 */
public interface NoticeTypeInfoService {
    /**
     * 更新消息类型
     *
     * @param noticeTypeInfo 消息类型
     * @return 更新行数
     */
    Integer updateNoticeTypeInfo(NoticeTypeInfo noticeTypeInfo);
    
    /**
     * 根据主键获取消息类型
     *
     * @param id
     * @return
     */
    NoticeTypeInfo getNoticeTypeInfoByKey(Long id);
    
    /**
     * 获取消息类型分页列表
     *
     * @param noticeTypePageReq 消息类型分页列表请求参数
     * @return 消息类型分页列表信息
     */
    PageInfo<NoticeTypeInfo> getPageInfoByReq(NoticeTypePageReq noticeTypePageReq);

    /**
     * 根据主键列表删除消息类型
     * @param ids 主键列表
     * @return 删除记录数
     */
    Integer deleteNoticeTypeByIds(List<Long> ids);
}
