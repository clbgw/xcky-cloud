package com.xcky.mapper;

import com.xcky.model.entity.ExamPaperQuestion;
import com.xcky.model.vo.ExamPaperQuestionPageVo;
import java.util.List;
import java.util.Map;

/**
 * 试卷与问题关联关系数据访问接口
 *
 * @author lbchen
 */
public interface ExamPaperQuestionMapper {
    /**
     * 根据map条件查询试卷与问题关联关系列表
     *
     * @param map map条件
     * @return 试卷与问题关联关系列表
     */
    List<ExamPaperQuestion> selectPaperQuestionsByMap(Map<String, Object> map);

    /**
     * 根据map条件查询试卷问题分页值对象列表
     *
     * @param map map条件
     * @return 试卷问题分页值对象列表
     */
    List<ExamPaperQuestionPageVo> selectPaperQuestionPageVosByMap(Map<String, Object> map);
}
