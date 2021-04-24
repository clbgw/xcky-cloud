package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.ExamPaper;
import com.xcky.model.req.ExamPaperPageReq;
import com.xcky.model.vo.ExamPaperPageVo;

/**
 * 试卷服务接口
 *
 * @author lbchen
 */
public interface ExamPaperService {
    /**
     * 根据试卷分页请求参数分页查询试卷分页列表
     *
     * @param examPaperPageReq 试卷分页请求参数
     * @return 试卷分页列表
     */
    PageInfo<ExamPaperPageVo> getPaperVoPageByReq(ExamPaperPageReq examPaperPageReq);

    /**
     * 根据试卷ID查询试卷详情
     *
     * @param paperId 试卷ID
     * @return 试卷实体类
     */
    ExamPaper getPaperVoByPaperId(Long paperId);
}
