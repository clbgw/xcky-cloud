package com.xcky.mapper;

import com.xcky.model.entity.ExamPaper;
import com.xcky.model.vo.ExamPaperPageVo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 试卷实体类
 *
 * @author lbchen
 */
public interface ExamPaperMapper {
    /**
     * 根据map条件查询试卷实体类列表
     *
     * @param map map条件
     * @return 试卷实体类列表
     */
    List<ExamPaper> selectPaperVosByMap(Map<String, Object> map);

    /**
     * 根据map条件查询试卷分页值对象列表
     *
     * @param map map条件
     * @return 试卷分页值对象列表
     */
    List<ExamPaperPageVo> selectPaperPageVosByMap(Map<String, Object> map);

    /**
     * 根据主键试卷ID查询试卷详情
     *
     * @param paperId 试卷ID
     * @return 试卷详情
     */
    ExamPaper selectPaperByPaperId(@Param("paperId") Long paperId);
}
