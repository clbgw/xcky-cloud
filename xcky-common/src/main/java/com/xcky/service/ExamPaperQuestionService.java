package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.req.ExamPaperQuestionPageReq;
import com.xcky.model.vo.ExamPaperQuestionPageVo;

/**
 * 试卷题目服务接口
 *
 * @author lbchen
 */
public interface ExamPaperQuestionService {
    /**
     * 根据试卷问题分页请求参数查询试卷问题分页列表
     *
     * @param examPaperQuestionPageReq 试卷问题分页请求参数
     * @return 试卷问题分页列表
     */
    PageInfo<ExamPaperQuestionPageVo> getPaperQuestionVoPageByReq(ExamPaperQuestionPageReq examPaperQuestionPageReq);
}
