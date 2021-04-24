package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.ThirtTokenInfo;
import com.xcky.model.req.ThirtTokenInfoPageReq;

import java.util.Map;

/**
 * 第三方会话信息服务接口
 *
 * @author lbchen
 */
public interface ThirtTokenInfoService {
    /**
     * 更新第三方会话信息
     *
     * @param thirtTokenInfo 第三方会话信息
     * @return 更新行数
     */
    Integer updateThirtTokenInfo(ThirtTokenInfo thirtTokenInfo);
    
    /**
     * 获取第三方会话信息详情
     *
     * @param id 主键ID
     * @return 第三方会话信息详情
     */
    ThirtTokenInfo getThirtTokenInfoById(Long id);
    
    /**
     * 通过map条件获取第三方会话信息详情
     * @param map map条件
     * @return 第三方会话信息详情
     */
    ThirtTokenInfo getThirtTokenInfoByMap(Map<String,Object> map);
    
    /**
     * 通过第三方会话信息分页请求参数获取分页列表
     *
     * @param thirtTokenInfoPageReq 第三方会话信息分页请求参数
     * @return 第三方会话信息分页列表
     */
    PageInfo<ThirtTokenInfo> getThirtTokenInfoByPageReq(ThirtTokenInfoPageReq thirtTokenInfoPageReq);

    /**
     * 通过APPID字符串获取第三方会话信息详情
     * @param appid 公众号APPID
     * @return 第三方会话信息详情
     */
    ThirtTokenInfo getThirtTokenInfoByAppid(String appid);
}
