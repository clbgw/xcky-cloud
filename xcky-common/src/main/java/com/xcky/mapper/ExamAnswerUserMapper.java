package com.xcky.mapper;

import com.xcky.model.entity.ExamAnswerUser;

/**
 * 用户与提交答案关联关系数据访问接口
 *
 * @author lbchen
 */
public interface ExamAnswerUserMapper {
    /**
     * 新增用户答题记录
     *
     * @param examAnswerUser 用户答题记录
     * @return 新增行数
     */
    Integer insertAnswerUser(ExamAnswerUser examAnswerUser);
}
