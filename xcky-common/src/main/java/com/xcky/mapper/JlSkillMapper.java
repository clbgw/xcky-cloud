package com.xcky.mapper;

import com.xcky.model.req.JlSkillInfoUpdateReq;
import com.xcky.model.vo.JlSkillVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历技能对象映射接口
 *
 * @author lbchen
 */
public interface JlSkillMapper {
    /**
     * 根据简历ID查询技能信息列表
     * @param jlId 简历ID
     * @return 简历技能信息列表
     */
    List<JlSkillVo> selectJlSkillVoByJlId(@Param("jlId") Long jlId);
    
    /**
     * 新增简历技能信息
     * @param jlSkillInfoUpdateReq 简历技能信息更新请求参数
     * @return 新增行数
     */
    Integer insertSkillInfo(JlSkillInfoUpdateReq jlSkillInfoUpdateReq);
    
    /**
     * 更新简历技能信息
     * @param jlSkillInfoUpdateReq 简历技能信息更新请求参数
     * @return 更新行数
     */
    Integer updateSkillInfo(JlSkillInfoUpdateReq jlSkillInfoUpdateReq);
}
