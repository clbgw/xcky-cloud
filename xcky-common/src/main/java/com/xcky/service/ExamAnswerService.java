package com.xcky.service;

import com.xcky.model.req.ExamQuestionPaperAnswerSubReq;
import com.xcky.model.vo.ExamAnswerVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 答案服务接口
 *
 * @author lbchen
 */
public interface ExamAnswerService {

    /**
     * 根据map条件查询答案对象列表
     *
     * @param map map条件
     * @return 答案对象列表
     */
    List<ExamAnswerVo> getAnswerVoByMap(Map<String, Object> map);

    /**
     * 获取正确答案列表
     *
     * @param questionId 问题ID
     * @param isRight    是否正确
     * @param status     是否启用
     * @return 答案列表
     */
    Set<Long> getQuestionRightAnswer(Long questionId, String isRight, String status);

    /**
     * 根据试卷ID获取所有题目对应的正确答案
     *
     * @param paperId 试卷ID
     * @return 试卷所有的正确答案
     */
    List<ExamQuestionPaperAnswerSubReq> getQuestionRightAnswersByPaperId(Long paperId);
}
