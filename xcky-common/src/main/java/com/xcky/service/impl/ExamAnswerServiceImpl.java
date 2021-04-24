package com.xcky.service.impl;


import com.xcky.mapper.ExamAnswerMapper;
import com.xcky.model.req.ExamQuestionPaperAnswerSubReq;
import com.xcky.model.vo.ExamAnswerVo;
import com.xcky.service.ExamAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 答案服务实现类
 *
 * @author lbchen
 */

@Service
public class ExamAnswerServiceImpl implements ExamAnswerService {
    @Autowired
    private ExamAnswerMapper examAnswerMapper;

    @Override
    public List<ExamAnswerVo> getAnswerVoByMap(Map<String, Object> map) {
        return examAnswerMapper.selectAnswerVoByMap(map);
    }

    @Override
    public Set<Long> getQuestionRightAnswer(Long questionId, String isRight, String status) {
        Map<String,Object> map = new HashMap<>(8);
        map.put("questionId",questionId);
        if(!StringUtils.isEmpty(isRight)) {
            map.put("isRight",isRight);
        }
        map.put("status",status);
        Set<Long> answerIds = examAnswerMapper.selectAnswerIdsByMap(map);
        return answerIds;
    }

    @Override
    public List<ExamQuestionPaperAnswerSubReq> getQuestionRightAnswersByPaperId(Long paperId) {
        List<ExamQuestionPaperAnswerSubReq> list = examAnswerMapper.selectRightAnswersByPaperId(paperId);
        return list;
    }
}
