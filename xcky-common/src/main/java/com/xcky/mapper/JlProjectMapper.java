package com.xcky.mapper;

import com.xcky.model.req.JlProjectInfoUpdateReq;
import com.xcky.model.vo.JlProjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历项目经历对象映射接口
 *
 * @author lbchen
 */
public interface JlProjectMapper {
    /**
     * 通过简历ID查询简历项目信息列表
     *
     * @param jlId 简历ID
     * @return 简历项目信息列表
     */
    List<JlProjectVo> selectJlProjectVoByJlId(@Param("jlId") Long jlId);
    
    /**
     * 新增简历项目信息
     *
     * @param jlProjectInfoUpdateReq 简历项目信息更新请求参数
     * @return 新增行数
     */
    Integer insertProjectInfo(JlProjectInfoUpdateReq jlProjectInfoUpdateReq);
    
    /**
     * 更新简历项目信息
     *
     * @param jlProjectInfoUpdateReq 简历项目信息更新请求参数
     * @return 更新行数
     */
    Integer updateProjectInfo(JlProjectInfoUpdateReq jlProjectInfoUpdateReq);
}
