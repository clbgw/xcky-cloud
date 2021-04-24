package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 简历信息值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlInfoVo {
    /**
     * 简历基本信息
     */
    private JlBasicInfoVo jlBasicInfoVo;
    /**
     * 简历技能信息
     */
    private List<JlSkillVo> jlSkillVos;
    /**
     * 简介教育学历信息
     */
    private List<JlEducationVo> jlEducationVos;
    /**
     * 奖励工作项目信息
     */
    private List<JlProjectVo> jlProjectVos;
    /**
     * 奖励工作信息
     */
    private List<JlWorkVo> jlWorkVos;
    
}
