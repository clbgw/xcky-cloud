package com.xcky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.ExamPaperQuestionMapper;
import com.xcky.model.req.ExamPaperQuestionPageReq;
import com.xcky.model.vo.ExamPaperQuestionPageVo;
import com.xcky.service.ExamPaperQuestionService;
import com.xcky.util.EntityUtil;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 试卷题目服务实现类
 *
 * @author lbchen
 */
@Service
public class ExamPaperQuestionServiceImpl implements ExamPaperQuestionService {
    @Autowired
    private ExamPaperQuestionMapper examPaperQuestionMapper;

    @Override
    public PageInfo<ExamPaperQuestionPageVo> getPaperQuestionVoPageByReq(ExamPaperQuestionPageReq examPaperQuestionPageReq) {
        Integer page = examPaperQuestionPageReq.getPage();
        Integer size = examPaperQuestionPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(examPaperQuestionPageReq);

        PageInfo<ExamPaperQuestionPageVo> pageInfo = PageHelper.startPage(page, size, false).doSelectPageInfo(() -> {
            examPaperQuestionMapper.selectPaperQuestionPageVosByMap(map);
        });
        // 重新计算总记录数
        List<ExamPaperQuestionPageVo> list = pageInfo.getList();
        if (null == list || list.size() < 1) {
            pageInfo.setTotal(0);
        } else {
            pageInfo.setTotal(list.size());
        }
        return pageInfo;
    }
}
