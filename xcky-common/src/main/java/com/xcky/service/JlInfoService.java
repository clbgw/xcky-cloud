package com.xcky.service;

import com.xcky.model.req.JlBasicInfoUpdateReq;
import com.xcky.model.req.JlEduInfoUpdateReq;
import com.xcky.model.req.JlProjectInfoUpdateReq;
import com.xcky.model.req.JlSkillInfoUpdateReq;
import com.xcky.model.req.JlWorkInfoUpdateReq;
import com.xcky.model.vo.JlBasicInfoVo;
import com.xcky.model.vo.JlEducationVo;
import com.xcky.model.vo.JlInfoVo;
import com.xcky.model.vo.JlProjectVo;
import com.xcky.model.vo.JlSkillVo;
import com.xcky.model.vo.JlWorkVo;
import com.xcky.model.vo.UserJlRelaVo;
import java.util.List;
import java.util.Map;

/**
 * 简历信息服务接口
 *
 * @author lbchen
 */
public interface JlInfoService {
    /**
     * 根据简历ID查询简历信息
     *
     * @param jlId 简历ID
     * @return 简历信息
     */
    JlInfoVo getJlInfoByJlId(Long jlId);

    /**
     * 更新简历基本信息
     *
     * @param jlBasicInfoUpdateReq 简历基本信息请求参数
     * @return 更新行数
     */
    Integer updateJlBasicInfo(JlBasicInfoUpdateReq jlBasicInfoUpdateReq);

    /**
     * 更新简历技能信息
     *
     * @param jlSkillInfoUpdateReq 简历技能信息请求参数
     * @return 更新行数
     */
    Integer updateJlSkillInfo(JlSkillInfoUpdateReq jlSkillInfoUpdateReq);

    /**
     * 更新简历教育信息
     *
     * @param jlEduInfoUpdateReq 简历学历信息请求参数
     * @return 更新行数
     */
    Integer updateJlEduInfo(JlEduInfoUpdateReq jlEduInfoUpdateReq);

    /**
     * 更新简历公司信息
     *
     * @param jlWorkInfoUpdateReq 简历公司信息请求参数
     * @return 更新行数
     */
    Integer updateJlWorkInfo(JlWorkInfoUpdateReq jlWorkInfoUpdateReq);

    /**
     * 更新简历项目信息
     *
     * @param jlProjectInfoUpdateReq 简历项目信息请求参数
     * @return 更新行数
     */
    Integer updateJlProjectInfo(JlProjectInfoUpdateReq jlProjectInfoUpdateReq);

    /**
     * 查询简历基本信息
     *
     * @param jlId 简历ID
     * @return 简历基本信息
     */
    JlBasicInfoVo getJlBasicInfoByJlId(Long jlId);

    /**
     * 查询简历技能信息列表
     *
     * @param jlId 简历ID
     * @return 简历技能信息列表
     */
    List<JlSkillVo> getJlSkillInfoByJlId(Long jlId);

    /**
     * 查询简历学历信息列表
     *
     * @param jlId 简历ID
     * @return 简历学历信息列表
     */
    List<JlEducationVo> getJlEducationInfoByJlId(Long jlId);

    /**
     * 查询简历工作信息列表
     *
     * @param jlId 简历ID
     * @return 简历工作信息列表
     */
    List<JlWorkVo> getJlWorkInfoByJlId(Long jlId);

    /**
     * 查询简历项目信息列表
     *
     * @param jlId 简历ID
     * @return 简历项目信息列表
     */
    List<JlProjectVo> getJlProjectInfoByJlId(Long jlId);

    /**
     * 更新用户简历关系
     *
     * @param userId 用户ID
     * @param jlId   简历ID
     * @param openid 用户OPENID
     * @param status 关系状态
     * @return 更新行数
     */
    Integer updateUserJlRela(Long userId, Long jlId, String openid, String status);

    /**
     * 根据map条件查询用户对应简历关系列表
     *
     * @param map map条件
     * @return 用户对应简历关系列表
     */
    List<UserJlRelaVo> getUserJlRelaListByMap(Map<String, Object> map);
}
