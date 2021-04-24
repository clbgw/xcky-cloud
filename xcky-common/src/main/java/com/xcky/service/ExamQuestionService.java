package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.req.ExamQuestionMarkPageReq;
import com.xcky.model.req.ExamQuestionPageReq;
import com.xcky.model.vo.ExamQuestionVo;

/**
 * 问题服务接口
 *
 * @author lbchen
 */
public interface ExamQuestionService {

    /**
     * 根据请求参数查询问题值对象分页列表
     *
     * @param examQuestionPageReq 请求参数
     * @return 问题值对象分页列表
     */
    PageInfo<ExamQuestionVo> getQuestionVoPageByReq(ExamQuestionPageReq examQuestionPageReq);

    /**
     * 根据问题ID查询问题值对象
     *
     * @param questionId 问题ID
     * @param hasAnswer 是否有答案
     * @return 问题值对象
     */
    ExamQuestionVo getQuestionVoByQuestionId(Long questionId,boolean hasAnswer);

    /**
     * 通过问题标记分页请求参数获取问题ID分页列表
     *
     * @param examQuestionMarkPageReq 问题标记分页请求参数
     * @return 问题ID分页列表
     */
    PageInfo<Long> getQuestionMarkPage(ExamQuestionMarkPageReq examQuestionMarkPageReq);

    /**
     * 根据问题ID查看问题解析
     *
     * @param questionId 问题ID
     * @return 问题解析
     */
    String getQuestionDescById(Long questionId);
}
