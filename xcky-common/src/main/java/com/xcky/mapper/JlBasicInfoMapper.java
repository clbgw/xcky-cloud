package com.xcky.mapper;

import com.xcky.model.req.JlBasicInfoUpdateReq;
import com.xcky.model.vo.JlBasicInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 简历基本信息对象映射接口
 *
 * @author lbchen
 */
public interface JlBasicInfoMapper {
    /**
     * 查询简历基本信息
     *
     * @param jlId 简历ID
     * @return 简历基本信息
     */
    JlBasicInfoVo selectJlBasicInfoVoByKey(@Param("jlId") Long jlId);
    
    /**
     * 新增简历基本信息
     *
     * @param jlBasicInfoUpdateReq 简历基本信息更新请求参数
     * @return 新增行数
     */
    Integer insertBasicInfo(JlBasicInfoUpdateReq jlBasicInfoUpdateReq);
    
    /**
     * 更新简历基本信息
     * @param jlBasicInfoUpdateReq 简历基本信息更新请求参数
     * @return 更新行数
     */
    Integer updateBasicInfo(JlBasicInfoUpdateReq jlBasicInfoUpdateReq);
}
