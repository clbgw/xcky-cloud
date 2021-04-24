package com.xcky.mapper;

import com.xcky.model.req.JlEduInfoUpdateReq;
import com.xcky.model.vo.JlEducationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历教育学历对象映射接口
 *
 * @author lbchen
 */
public interface JlEducationMapper {
    /**
     * 根据简历ID查询学历信息列表
     *
     * @param jlId 简历ID
     * @return 学历信息列表
     */
    List<JlEducationVo> selectJlEducationVoByJlId(@Param("jlId") Long jlId);
    
    /**
     * 新增学历信息
     *
     * @param jlEduInfoUpdateReq 学历信息更新请求参数
     * @return 新增行数
     */
    Integer insertEduInfo(JlEduInfoUpdateReq jlEduInfoUpdateReq);
    
    /**
     * 更新学历信息
     *
     * @param jlEduInfoUpdateReq 学历信息更新请求参数
     * @return 新增行数
     */
    Integer updateEduInfo(JlEduInfoUpdateReq jlEduInfoUpdateReq);
}
