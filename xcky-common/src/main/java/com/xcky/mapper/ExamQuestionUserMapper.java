package com.xcky.mapper;

import com.xcky.model.entity.ExamQuestionUser;
import java.util.List;
import java.util.Map;

/**
 * 问题与人关联关系数据访问接口
 *
 * @author lbchen
 */
public interface ExamQuestionUserMapper {
    /**
     * 根据map条件查询问题与人关联关系列表
     *
     * @param map map条件
     * @return 问题与人关联关系列表
     */
    List<ExamQuestionUser> selectExamQuestionUserByMap(Map<String, Object> map);

    /**
     * 新增问题与人关联关系
     *
     * @param examQuestionUser 问题与人关联关系
     * @return 新增行数
     */
    Integer insertExamQuestionUser(ExamQuestionUser examQuestionUser);

    /**
     * 更新问题与人关联关系
     *
     * @param examQuestionUser 问题与人关联关系
     * @return 更新行数
     */
    Integer updateExamQuestionUser(ExamQuestionUser examQuestionUser);
}
