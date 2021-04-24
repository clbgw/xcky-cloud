package com.xcky.mapper;

import com.xcky.model.req.JlWorkInfoUpdateReq;
import com.xcky.model.vo.JlWorkVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历工作经历对象映射接口
 *
 * @author lbchen
 */
public interface JlWorkMapper {
    /**
     * 通过简历ID查询简历工作公司信息列表
     *
     * @param jlId 简历ID
     * @return 简历工作公司信息列表
     */
    List<JlWorkVo> selectJlWorkVoByJlId(@Param("jlId") Long jlId);
    
    /**
     * 新增简历公司工作信息
     *
     * @param jlWorkInfoUpdateReq 简历工作公司更新请求参数
     * @return 新增行数
     */
    Integer insertWorkInfo(JlWorkInfoUpdateReq jlWorkInfoUpdateReq);
    
    /**
     * 更新简历公司工作信息
     *
     * @param jlWorkInfoUpdateReq 简历工作公司更新请求参数
     * @return 更新行数
     */
    Integer updateWorkInfo(JlWorkInfoUpdateReq jlWorkInfoUpdateReq);
}
