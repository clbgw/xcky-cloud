package com.xcky.mapper;

import com.xcky.model.vo.ExamQuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 问题数据访问接口
 *
 * @author lbchen
 */
public interface ExamQuestionMapper {

    /**
     * 根据map条件查询问题列表
     *
     * @param map map条件
     * @return 问题值对象列表
     */
    List<ExamQuestionVo> selectQuestionVoByMap(Map<String, Object> map);

    /**
     * 查看问题解析
     *
     * @param questionId 问题ID
     * @return 问题解析
     */
    String selectQuestionDescById(@Param("questionId") Long questionId);

    /**
     * 根据map条件查询问题ID列表
     *
     * @param map map条件
     * @return 问题ID列表
     */
    List<Long> selectQUestionIdsByMap(Map<String, Object> map);
}
