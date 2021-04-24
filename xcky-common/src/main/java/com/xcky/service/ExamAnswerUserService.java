package com.xcky.service;

import com.xcky.model.entity.ExamAnswerUser;

/**
 * 用户答题服务接口
 *
 * @author lbchen
 */
public interface ExamAnswerUserService {
    /**
     * 保存用户答题记录
     *
     * @param examAnswerUser 用户与提交答案关联关系
     * @return 新增行数
     */
    Integer saveExamAnswerUser(ExamAnswerUser examAnswerUser);


}
