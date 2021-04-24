package com.xcky.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xcky.annotation.LoginAuthAnnotation;
import com.xcky.convert.NoticeTypeInfoConvert;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.NoticeTypeInfo;
import com.xcky.model.req.NoticeTypeDeleteReq;
import com.xcky.model.req.NoticeTypeDetailReq;
import com.xcky.model.req.NoticeTypePageReq;
import com.xcky.model.req.NoticeTypeUpdateReq;
import com.xcky.model.resp.R;
import com.xcky.service.NoticeTypeInfoService;
import com.xcky.util.ResponseUtil;

/**
 * 消息类型控制器
 *
 * @author lbchen
 */
@RestController
public class NoticeTypeInfoController {
    @Autowired
    private NoticeTypeInfoService noticeTypeInfoService;

    /**
     * 更新消息类型
     *
     * @param noticeTypeUpdateReq 消息类型更新请求参数
     * @return 响应对象
     */
    @LoginAuthAnnotation
    @PostMapping("/noticeType/update")
    public R update(@RequestBody NoticeTypeUpdateReq noticeTypeUpdateReq) {
        NoticeTypeInfo noticeTypeInfo = new NoticeTypeInfo();
        noticeTypeInfo = NoticeTypeInfoConvert.getNoticeTypeInfoByUpdateReq(noticeTypeUpdateReq);
        if (null == noticeTypeInfo) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = noticeTypeInfoService.updateNoticeTypeInfo(noticeTypeInfo);
        if (null == updateResult || updateResult < 1) {
            throw new BizException(ResponseEnum.INSERT_OR_UPDATE_ERROR, null);
        }
        return ResponseUtil.ok();
    }

    /**
     * 删除消息类型
     *
     * @param noticeTypeDeleteReq 消息类型删除请求参数
     * @return 响应对象
     */
    @LoginAuthAnnotation
    @PostMapping("/noticeType/delete")
    public R delete(@RequestBody NoticeTypeDeleteReq noticeTypeDeleteReq) {
        List<Long> ids = noticeTypeDeleteReq.getIds();
        if (null == ids || ids.size() < 1) {
            throw new BizException(ResponseEnum.NOT_SELECT_DATA, null);
        }
        Integer deleteResult = noticeTypeInfoService.deleteNoticeTypeByIds(ids);
        if (null == deleteResult || deleteResult < 1) {
            throw new BizException(ResponseEnum.DELETE_ERROR, null);
        }
        return ResponseUtil.ok();
    }

    /**
     * 获取消息类型详细信息
     *
     * @param noticeTypeDetailReq 获取消息类型详情请求对象
     * @return 响应对象
     */
    @LoginAuthAnnotation
    @PostMapping("/noticeType/getDetail")
    public R getDetail(@RequestBody NoticeTypeDetailReq noticeTypeDetailReq) {
        if (null == noticeTypeDetailReq || null == noticeTypeDetailReq.getId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long id = noticeTypeDetailReq.getId();
        NoticeTypeInfo noticeTypeInfo = noticeTypeInfoService.getNoticeTypeInfoByKey(id);
        if (null == noticeTypeInfo) {
            throw new BizException(ResponseEnum.NO_DATA, null);
        }
        return ResponseUtil.ok(noticeTypeInfo);
    }

    /**
     * 获取消息类型分页列表
     *
     * @param noticeTypePageReq 获取消息类型分页请求对象
     * @return 响应对象
     */
    @LoginAuthAnnotation
    @PostMapping("/noticeType/getPage")
    public R getPage(@RequestBody NoticeTypePageReq noticeTypePageReq) {
        PageInfo<NoticeTypeInfo> pageInfo = noticeTypeInfoService.getPageInfoByReq(noticeTypePageReq);
        return ResponseUtil.page(pageInfo);
    }

}
