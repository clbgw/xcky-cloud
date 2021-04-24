package com.xcky.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.xcky.convert.NoticeDistributeConfigConvert;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.NoticeDistributeConfig;
import com.xcky.model.req.NoticeDistributeConfigDeleteReq;
import com.xcky.model.req.NoticeDistributeConfigDetailReq;
import com.xcky.model.req.NoticeDistributeConfigPageReq;
import com.xcky.model.req.NoticeDistributeConfigUpdateReq;
import com.xcky.model.resp.R;
import com.xcky.service.NoticeDistributeConfigService;
import com.xcky.util.EntityUtil;
import com.xcky.util.ResponseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 消息分发配置控制器
 *
 * @author lbchen
 */
@Api(tags = "消息分发配置")
@RestController
public class NoticeDistributeConfigController {
    @Autowired
    private NoticeDistributeConfigService noticeDistributeConfigService;

    /**
     * 更新消息分发配置
     *
     * @param noticeDistributeConfigUpdateReq 消息分发配置更新请求参数
     * @return 响应对象
     */
    @ApiOperation("更新")
    @PostMapping("/noticeDistributeConfig/update")
    public R update(@RequestBody NoticeDistributeConfigUpdateReq noticeDistributeConfigUpdateReq) {
        NoticeDistributeConfig noticeDistributeConfig = NoticeDistributeConfigConvert.getNoticeDistributeConfigByUpdateReq(noticeDistributeConfigUpdateReq);
        if (null == noticeDistributeConfig) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = noticeDistributeConfigService.updateNoticeDistributeConfig(noticeDistributeConfig);
        if (null == updateResult || updateResult < 1) {
            throw new BizException(ResponseEnum.INSERT_OR_UPDATE_ERROR, null);
        }
        return ResponseUtil.ok();
    }

    /**
     * 获取消息分发配置分页列表
     *
     * @param noticeDistributeConfigPageReq 消息分发配置分页请求参数
     * @return 响应对象
     */
    @ApiOperation("获取分页列表")
    @PostMapping("/noticeDistributeConfig/getPage")
    public R getPage(@RequestBody NoticeDistributeConfigPageReq noticeDistributeConfigPageReq) {
        PageInfo<NoticeDistributeConfig> pageInfo = noticeDistributeConfigService.getPageInfo(noticeDistributeConfigPageReq);
        return ResponseUtil.page(pageInfo);
    }

    /**
     * 获取消息分发配置详情
     *
     * @param noticeDistributeConfigDetailReq 消息分发配置详情请求参数
     * @return 响应对象
     */
    @ApiOperation("获取详情")
    @PostMapping("/noticeDistributeConfig/getDetail")
    public R getDetail(@RequestBody NoticeDistributeConfigDetailReq noticeDistributeConfigDetailReq) {
        if (null == noticeDistributeConfigDetailReq || null == noticeDistributeConfigDetailReq.getId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long id = noticeDistributeConfigDetailReq.getId();
        NoticeDistributeConfig noticeDistributeConfig = noticeDistributeConfigService.getNoticeDistributeConfigDetailByKey(id);
        if (null == noticeDistributeConfig) {
            throw new BizException(ResponseEnum.NO_DATA, null);
        }
        return ResponseUtil.ok(noticeDistributeConfig);
    }

    /**
     * 删除消息分发配置
     *
     * @param noticeDistributeConfigDeleteReq 消息分发配置删除请求参数
     * @return 响应对象
     */
    @ApiOperation("删除")
    @PostMapping("/noticeDistributeConfig/delete")
    public R getDetail(@RequestBody NoticeDistributeConfigDeleteReq noticeDistributeConfigDeleteReq) {
        if (null == noticeDistributeConfigDeleteReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Map<String, Object> map = EntityUtil.entityToMap(noticeDistributeConfigDeleteReq);
        Integer deleteResult = noticeDistributeConfigService.deleteNoticeDistributeConfigByMap(map);
        if (null == deleteResult || deleteResult < 1) {
            throw new BizException(ResponseEnum.DELETE_ERROR, null);
        }
        return ResponseUtil.ok();
    }
}
