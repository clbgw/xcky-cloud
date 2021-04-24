package com.xcky.controller;

import com.xcky.enums.ResponseEnum;
import com.xcky.enums.UserJlRelaStatusEnum;
import com.xcky.exception.BizException;
import com.xcky.model.req.JlBasicInfoUpdateReq;
import com.xcky.model.req.JlEduInfoUpdateReq;
import com.xcky.model.req.JlInfoReq;
import com.xcky.model.req.JlProjectInfoUpdateReq;
import com.xcky.model.req.JlSelfListReq;
import com.xcky.model.req.JlSkillInfoUpdateReq;
import com.xcky.model.req.JlWorkInfoUpdateReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.JlBasicInfoVo;
import com.xcky.model.vo.JlEducationVo;
import com.xcky.model.vo.JlProjectVo;
import com.xcky.model.vo.JlSkillVo;
import com.xcky.model.vo.JlWorkVo;
import com.xcky.model.vo.UserJlRelaVo;
import com.xcky.service.JlInfoService;
import com.xcky.util.ResponseUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 简历信息控制器
 *
 * @author lbchen
 */
@RestController
public class JlInfoController {
    @Autowired
    private JlInfoService jlInfoService;

    /**
     * 获取个人简历列表
     *
     * @param jlSelfListReq 获取个人简历列表请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/getInfo/selfList")
    public R getSelfJlList(@RequestBody JlSelfListReq jlSelfListReq) {
        if (null == jlSelfListReq || null == jlSelfListReq.getOpenid()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("status", UserJlRelaStatusEnum.VALID.getCode());
        map.put("userId", jlSelfListReq.getUserId());
        map.put("openid", jlSelfListReq.getOpenid());
        List<UserJlRelaVo> list = jlInfoService.getUserJlRelaListByMap(map);
        return ResponseUtil.ok(list);
    }

    /**
     * 获取简历全部信息
     *
     * @param jlInfoReq 简历信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/getInfo")
    public R getJlInfoByJlId(@RequestBody JlInfoReq jlInfoReq) {
        if (null == jlInfoReq || null == jlInfoReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long jlId = jlInfoReq.getJlId();
        return ResponseUtil.ok(jlInfoService.getJlInfoByJlId(jlId));
    }

    /**
     * 获取简历基本信息
     *
     * @param jlInfoReq 简历信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/getInfo/basic")
    public R getJlBasicInfo(@RequestBody JlInfoReq jlInfoReq) {
        if (null == jlInfoReq || null == jlInfoReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long jlId = jlInfoReq.getJlId();
        JlBasicInfoVo jlBasicInfoVo = jlInfoService.getJlBasicInfoByJlId(jlId);
        return ResponseUtil.ok(jlBasicInfoVo);
    }

    /**
     * 获取简历技能信息
     *
     * @param jlInfoReq 简历技能信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/getInfo/skill")
    public R getJlSkillInfo(@RequestBody JlInfoReq jlInfoReq) {
        if (null == jlInfoReq || null == jlInfoReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long jlId = jlInfoReq.getJlId();
        List<JlSkillVo> list = jlInfoService.getJlSkillInfoByJlId(jlId);
        return ResponseUtil.ok(list);
    }

    /**
     * 获取简历学历信息
     *
     * @param jlInfoReq 简历学历信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/getInfo/edu")
    public R getJlEduInfo(@RequestBody JlInfoReq jlInfoReq) {
        if (null == jlInfoReq || null == jlInfoReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long jlId = jlInfoReq.getJlId();
        List<JlEducationVo> list = jlInfoService.getJlEducationInfoByJlId(jlId);
        return ResponseUtil.ok(list);
    }

    /**
     * 获取简历工作信息
     *
     * @param jlInfoReq 简历工作信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/getInfo/work")
    public R getJlWorkInfoByJlId(@RequestBody JlInfoReq jlInfoReq) {
        if (null == jlInfoReq || null == jlInfoReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long jlId = jlInfoReq.getJlId();
        List<JlWorkVo> list = jlInfoService.getJlWorkInfoByJlId(jlId);
        return ResponseUtil.ok(list);
    }

    /**
     * 获取简历项目信息
     *
     * @param jlInfoReq 简历项目信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/getInfo/project")
    public R getJlProjectInfoByJlId(@RequestBody JlInfoReq jlInfoReq) {
        if (null == jlInfoReq || null == jlInfoReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long jlId = jlInfoReq.getJlId();
        List<JlProjectVo> list = jlInfoService.getJlProjectInfoByJlId(jlId);
        return ResponseUtil.ok(list);
    }

    /**
     * 创建或更新简历基本信息
     *
     * @param jlBasicInfoUpdateReq 简历基本信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/saveBasicInfo")
    public R saveBasicInfo(@RequestBody JlBasicInfoUpdateReq jlBasicInfoUpdateReq) {
        if (null == jlBasicInfoUpdateReq || null == jlBasicInfoUpdateReq.getOpenid()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = jlInfoService.updateJlBasicInfo(jlBasicInfoUpdateReq);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        // 更新用户简历关系
        jlInfoService.updateUserJlRela(jlBasicInfoUpdateReq.getUserId(),
                jlBasicInfoUpdateReq.getJlId(),
                jlBasicInfoUpdateReq.getOpenid(),
                UserJlRelaStatusEnum.VALID.getCode());
        // 返回简历ID
        return ResponseUtil.ok(jlBasicInfoUpdateReq.getJlId());
    }

    /**
     * 创建或更新简历技能信息
     *
     * @param jlSkillInfoUpdateReq 创建或更新简历技能信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/updateSkillInfo")
    public R updateSkillInfo(@RequestBody JlSkillInfoUpdateReq jlSkillInfoUpdateReq) {
        if (null == jlSkillInfoUpdateReq || null == jlSkillInfoUpdateReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = jlInfoService.updateJlSkillInfo(jlSkillInfoUpdateReq);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok(jlSkillInfoUpdateReq.getId());
    }

    /**
     * 创建或更新简历学历信息
     *
     * @param jlEduInfoUpdateReq 创建或更新简历学历信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/updateEduInfo")
    public R updateEduInfo(@RequestBody JlEduInfoUpdateReq jlEduInfoUpdateReq) {
        if (null == jlEduInfoUpdateReq || null == jlEduInfoUpdateReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = jlInfoService.updateJlEduInfo(jlEduInfoUpdateReq);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok(jlEduInfoUpdateReq.getId());
    }

    /**
     * 创建或更新简历工作信息
     *
     * @param jlWorkInfoUpdateReq 创建或更新简历工作信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/updateWorkInfo")
    public R updateWorkInfo(@RequestBody JlWorkInfoUpdateReq jlWorkInfoUpdateReq) {
        if (null == jlWorkInfoUpdateReq || null == jlWorkInfoUpdateReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = jlInfoService.updateJlWorkInfo(jlWorkInfoUpdateReq);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok(jlWorkInfoUpdateReq.getId());
    }

    /**
     * 创建或者更新简历项目信息
     *
     * @param jlProjectInfoUpdateReq 创建或者更新简历项目信息请求参数
     * @return 基本响应对象
     */
    @PostMapping("/jl/updateProjctInfo")
    public R updateProjctInfo(@RequestBody JlProjectInfoUpdateReq jlProjectInfoUpdateReq) {
        if (null == jlProjectInfoUpdateReq || null == jlProjectInfoUpdateReq.getJlId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Integer updateResult = jlInfoService.updateJlProjectInfo(jlProjectInfoUpdateReq);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok(jlProjectInfoUpdateReq.getId());
    }

}
