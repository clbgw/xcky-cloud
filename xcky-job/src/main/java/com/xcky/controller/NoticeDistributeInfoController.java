package com.xcky.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.NoticeDistributeInfo;
import com.xcky.model.req.NoticeDistributeInfoDetailReq;
import com.xcky.model.req.NoticeDistributeInfoNumReq;
import com.xcky.model.req.NoticeDistributeInfoPageReq;
import com.xcky.model.req.NoticeDistributeInfoUpdateStatusReq;
import com.xcky.model.resp.NoticeDistributeInfoNumResp;
import com.xcky.model.resp.R;
import com.xcky.service.NoticeDistributeInfoService;
import com.xcky.util.ResponseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 消息分发内容控制器
 *
 * @author lbchen
 */
@Api(tags = "消息分发内容")
@RestController
public class NoticeDistributeInfoController {
    @Autowired
    private NoticeDistributeInfoService noticeDistributeInfoService;
    
    /**
     * 获取消息分发内容详情
     *
     * @param noticeDistributeInfoDetailReq 消息分发内容详情请求参数
     * @return 消息分发内容详情
     */
    @ApiOperation("获取详情")
    @PostMapping("/noticeDistributeInfo/getDetail")
    public R getDetail(@RequestBody NoticeDistributeInfoDetailReq noticeDistributeInfoDetailReq) {
        if (null == noticeDistributeInfoDetailReq || null == noticeDistributeInfoDetailReq.getId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long id = noticeDistributeInfoDetailReq.getId();
        NoticeDistributeInfo noticeDistributeInfo = noticeDistributeInfoService.getDetailByKey(id);
        if (null == noticeDistributeInfo) {
            throw new BizException(ResponseEnum.NO_DATA, null);
        }
        return ResponseUtil.ok(noticeDistributeInfo);
    }
    
    /**
     * 获取消息分发内容分页列表
     *
     * @param noticeDistributeInfoPageReq 消息分发内容分页请求参数
     * @return 响应对象
     */
    @ApiOperation("获取分页列表")
    @PostMapping("/noticeDistributeInfo/getPage")
    public R getPage(@RequestBody NoticeDistributeInfoPageReq noticeDistributeInfoPageReq) {
        PageInfo<NoticeDistributeInfo> pageInfo = noticeDistributeInfoService.getPageInfo(noticeDistributeInfoPageReq);
        return ResponseUtil.page(pageInfo);
    }
    
    /**
     * 获取各类别的消息分发内容数量
     *
     * @param noticeDistributeInfoNumReq 消息分发内容数量请求参数
     * @return 响应对象
     */
    @ApiOperation("获取各类别数量统计")
    @PostMapping("/noticeDistributeInfo/getNum")
    public R getNum(@RequestBody NoticeDistributeInfoNumReq noticeDistributeInfoNumReq) {
        if(null == noticeDistributeInfoNumReq || null == noticeDistributeInfoNumReq.getUserId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR,null);
        }
        Integer userId = noticeDistributeInfoNumReq.getUserId();
        List<NoticeDistributeInfoNumResp> noticeDistributeInfoNumResps = noticeDistributeInfoService.getNumByUserId(userId);
        return ResponseUtil.ok(noticeDistributeInfoNumResps);
    }
    
    /**
     * 更新消息分发内容状态
     *
     * @param noticeDistributeInfoUpdateStatusReq 消息分发内容更新状态请求对象
     * @return 响应对象
     */
    @ApiOperation("更新状态")
    @PostMapping("/noticeDistributeInfo/updateStatus")
    public R updateStatus(@RequestBody NoticeDistributeInfoUpdateStatusReq noticeDistributeInfoUpdateStatusReq) {
        Integer updateResult = noticeDistributeInfoService.updateNoticeDistributeInfoStatus(noticeDistributeInfoUpdateStatusReq);
        if (null == updateResult || updateResult < 1) {
            throw new BizException(ResponseEnum.UPDATE_ERROR, null);
        }
        return ResponseUtil.ok();
    }
}
