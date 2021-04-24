package com.xcky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xcky.convert.NoticeInfoConvert;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.NoticeInfo;
import com.xcky.model.req.NoticeInfoDeleteReq;
import com.xcky.model.req.NoticeInfoDetailReq;
import com.xcky.model.req.NoticeInfoPageReq;
import com.xcky.model.req.NoticeInfoUpdateReq;
import com.xcky.model.resp.R;
import com.xcky.service.NoticeInfoService;
import com.xcky.util.ResponseUtil;

/**
 * 消息信息控制器
 *
 * @author lbchen
 */
@RestController
public class NoticeInfoController {
    @Autowired
    private NoticeInfoService noticeInfoService;
    
    /**
     * 新增或更新消息信息
     *
     * @param noticeInfoUpdateReq 消息信息更新请求参数
     * @return 响应对象
     */
    @PostMapping("/noticeInfo/update")
    public R update(@RequestBody NoticeInfoUpdateReq noticeInfoUpdateReq) {
        NoticeInfo noticeInfo = NoticeInfoConvert.getNoticeInfoByUpdateReq(noticeInfoUpdateReq);
        if (null == noticeInfo) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = noticeInfoService.updateNoticeInfo(noticeInfo);
        if (null == updateResult || updateResult < 1) {
            throw new BizException(ResponseEnum.INSERT_OR_UPDATE_ERROR, null);
        }
        return ResponseUtil.ok();
    }
    
    /**
     * 查询消息信息分页列表
     *
     * @param noticeInfoPageReq 消息信息分页请求参数
     * @return 响应对象
     */
    @PostMapping("/noticeInfo/getPage")
    public R getPage(@RequestBody NoticeInfoPageReq noticeInfoPageReq) {
        PageInfo<NoticeInfo> pageInfo = noticeInfoService.getPageInfo(noticeInfoPageReq);
        return ResponseUtil.page(pageInfo);
    }
    
    /**
     * 查询消息信息详情
     *
     * @param noticeInfoDetailReq 消息信息详情请求参数
     * @return 响应对象
     */
    @PostMapping("/noticeInfo/getDetail")
    public R getDetail(@RequestBody NoticeInfoDetailReq noticeInfoDetailReq) {
        if (null == noticeInfoDetailReq || null == noticeInfoDetailReq.getId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long id = noticeInfoDetailReq.getId();
        NoticeInfo noticeInfo = noticeInfoService.getDetail(id);
        if (null == noticeInfo) {
            throw new BizException(ResponseEnum.NO_DATA, null);
        }
        return ResponseUtil.ok(noticeInfo);
    }
    
    /**
     * 删除消息信息
     *
     * @param noticeInfoDeleteReq 消息信息删除请求参数
     * @return 响应对象
     */
    @PostMapping("/noticeInfo/delete")
    public R delete(@RequestBody NoticeInfoDeleteReq noticeInfoDeleteReq) {
        if (null == noticeInfoDeleteReq || null == noticeInfoDeleteReq.getIds()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer deleteResult = noticeInfoService.deleteNoticeInfoByMap(noticeInfoDeleteReq);
        if (null == deleteResult || deleteResult < 1) {
            throw new BizException(ResponseEnum.DELETE_ERROR, null);
        }
        return ResponseUtil.ok();
    }
}
