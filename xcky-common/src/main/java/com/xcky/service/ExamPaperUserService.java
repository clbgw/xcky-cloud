package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.ExamPaperUser;
import com.xcky.model.req.ExamPaperUserPageReq;
import com.xcky.model.vo.ExamPaperUserPageVo;

/**
 * 用户考试服务接口
 *
 * @author lbchen
 */
public interface ExamPaperUserService {
    /**
     * 保存用户考试记录
     *
     * @param examPaperUser 用户考试记录
     * @return 新增
     */
    Integer savePaperUser(ExamPaperUser examPaperUser);

    /**
     * 根据用户考试记录ID和用户ID查询试卷ID
     *
     * @param paperUserId 用户考试记录ID
     * @param userId      用户ID
     * @return 试卷ID
     */
    Long getPreparId(Long paperUserId, Long userId);

    /**
     * 根据主键ID查询用户考试记录
     *
     * @param paperUserId 主键ID
     * @return 用户考试记录
     */
    ExamPaperUser getPaperUserInfoById(Long paperUserId);

    /**
     * 根据分页请求参数查询用户答卷列表
     *
     * @param examPaperUserPageReq 用户答卷分页请求参数
     * @return 用户答卷列表
     */
    PageInfo<ExamPaperUserPageVo> getPaperUserPageByReq(ExamPaperUserPageReq examPaperUserPageReq);
}
