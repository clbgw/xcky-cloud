package com.xcky.mapper;


import com.xcky.model.entity.ExamPaperUser;
import com.xcky.model.vo.ExamPaperUserPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 试卷与人的关联关系数据访问接口
 *
 * @author lbchen
 */
public interface ExamPaperUserMapper {

    /**
     * 新增试卷与人的关联关系
     *
     * @param examPaperUser 试卷与人的关联关系
     * @return 新增行数
     */
    Integer insertExamPaperUser(ExamPaperUser examPaperUser);

    /**
     * 更新试卷与人的关联关系
     *
     * @param examPaperUser 试卷与人的关联关系
     * @return 影响行数
     */
    Integer updateExamPaperUser(ExamPaperUser examPaperUser);

    /**
     * 根据主键ID和用户ID查询试卷ID
     *
     * @param paperUserId 主键ID
     * @param userId      用户ID
     * @return 试卷ID
     */
    Long selectPaperId(@Param("id") Long paperUserId, @Param("userId") Long userId);

    /**
     * 根据主键查询用户考试记录
     *
     * @param id 主键ID
     * @return 用户考试记录
     */
    ExamPaperUser selectPaperUserById(@Param("id") Long id);

    /**
     * 根据map条件查询用户答卷分页列表
     *
     * @param map map条件
     * @return 用户答卷分页列表
     */
    List<ExamPaperUserPageVo> selectPaperUserPageVoByMap(Map<String, Object> map);
}
