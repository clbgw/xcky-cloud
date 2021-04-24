package com.xcky.controller;

import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.ParamStatusInfo;
import com.xcky.model.req.UserParamStatusGetReq;
import com.xcky.model.req.UserParamStatusSetReq;
import com.xcky.model.resp.R;
import com.xcky.service.ParamInfoService;
import com.xcky.service.ParamStatusInfoService;
import com.xcky.service.UserInfoService;
import com.xcky.util.Constants;
import com.xcky.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数状态信息控制器
 *
 * @author lbchen
 */
@RestController
public class ParamStatusInfoController {
    @Autowired
    private ParamStatusInfoService paramStatusInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ParamInfoService paramInfoService;

    /**
     * 获取用户参数值
     *
     * @param userParamStatusGetReq 用户状态获取请求参数
     * @return 基本响应对象
     */
    @PostMapping("/paramStatus/get")
    public R getParamStatusInfo(@RequestBody UserParamStatusGetReq userParamStatusGetReq) {
        if (null == userParamStatusGetReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        String type = userParamStatusGetReq.getType();
        // 鉴别参数类型是否预设
        paramInfoService.judgeParamValid(Constants.PARAM_CLASS_CODE, type);
        Long userId = userParamStatusGetReq.getUserId();
        // 鉴别用户ID是否存在
        userInfoService.judgeUserExist(userId);
        // 查询参数状态信息
        ParamStatusInfo paramStatusInfo = paramStatusInfoService.getParamStatusInfoByKey(type, userId);
        if (null == paramStatusInfo) {
            return ResponseUtil.fail(ResponseEnum.NO_DATA);
        }
        return ResponseUtil.ok(paramStatusInfo);
    }

    /**
     * 设置用户参数值
     *
     * @param userParamStatusSetReq 设置用户状态请求参数
     * @return 基本响应对象
     */
    @PostMapping("/paramStatus/set")
    public R setParamStatusInfo(@RequestBody UserParamStatusSetReq userParamStatusSetReq) {
        if (null == userParamStatusSetReq) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        String type = userParamStatusSetReq.getType();
        // 鉴别参数类型是否预设
        paramInfoService.judgeParamValid(Constants.PARAM_CLASS_CODE, type);
        Long userId = userParamStatusSetReq.getUserId();
        // 鉴别用户ID是否存在
        userInfoService.judgeUserExist(userId);
        ParamStatusInfo paramStatusInfo = new ParamStatusInfo();
        paramStatusInfo.setType(type);
        paramStatusInfo.setUserId(userId);
        paramStatusInfo.setStatus(userParamStatusSetReq.getStatus());
        Integer updateResult = paramStatusInfoService.updateParamStatusInfo(paramStatusInfo);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok();
    }
}
