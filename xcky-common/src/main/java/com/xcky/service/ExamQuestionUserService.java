package com.xcky.service;

import com.xcky.model.entity.ExamQuestionUser;

/**
 * 问题与用户管理关系服务接口
 *
 * @author lbchen
 */
public interface ExamQuestionUserService {
    /**
     * 获取问题的标记记录
     *
     * @param userId     用户ID
     * @param questionId 问题ID
     * @return 问题的标记记录
     */
    ExamQuestionUser getExamQuestionUserOne(Long userId, Long questionId);

    /**
     * 新增或更新问题标记记录
     *
     * @param examQuestionUser 问题标记记录
     * @return 影响行数
     */
    Integer updateExamQuestionUser(ExamQuestionUser examQuestionUser);
}
