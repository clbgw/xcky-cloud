package com.xcky.mapper;

import com.xcky.model.req.ExamQuestionPaperAnswerSubReq;
import com.xcky.model.vo.ExamAnswerVo;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * 答案数据访问接口
 *
 * @author lbchen
 */
public interface ExamAnswerMapper {
    /**
     * 查询答案对象列表
     *
     * @param map map条件
     * @return 答案对象列表
     */
    List<ExamAnswerVo> selectAnswerVoByMap(Map<String, Object> map);

    /**
     * 根据map条件查询答案ID列表
     *
     * @param map map条件
     * @return 答案ID列表
     */
    Set<Long> selectAnswerIdsByMap(Map<String, Object> map);
    /**
     * 根据试卷ID获取所有题目对应的正确答案
     *
     * @param paperId 试卷ID
     * @return 试卷所有的正确答案
     */
    List<ExamQuestionPaperAnswerSubReq> selectRightAnswersByPaperId(@Param("paperId") Long paperId);
}
