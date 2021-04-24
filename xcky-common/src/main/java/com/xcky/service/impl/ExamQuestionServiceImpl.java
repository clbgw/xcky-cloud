package com.xcky.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.ExamQuestionMapper;
import com.xcky.model.req.ExamQuestionMarkPageReq;
import com.xcky.model.req.ExamQuestionPageReq;
import com.xcky.model.vo.ExamQuestionVo;
import com.xcky.service.ExamQuestionService;
import com.xcky.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 问题服务实现类
 *
 * @author lbchen
 */
@Service
public class ExamQuestionServiceImpl implements ExamQuestionService {
    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Override
    public PageInfo<ExamQuestionVo> getQuestionVoPageByReq(ExamQuestionPageReq examQuestionPageReq) {
        Integer page = examQuestionPageReq.getPage();
        Integer size = examQuestionPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(examQuestionPageReq);
        PageInfo<ExamQuestionVo> pageInfo = PageHelper.startPage(page, size, false).doSelectPageInfo(() -> {
            examQuestionMapper.selectQuestionVoByMap(map);
        });
        //TODO 重新计算总记录数
        List<ExamQuestionVo> list = pageInfo.getList();
        if (null == list || list.size() < 1) {
            pageInfo.setTotal(0);
        } else {
            pageInfo.setTotal(list.size());
        }
        return pageInfo;
    }

    @Override
    public ExamQuestionVo getQuestionVoByQuestionId(Long questionId,boolean hasAnswer) {
        Map<String, Object> map = new HashMap<>();
        map.put("questionId", questionId);
        map.put("status", 1);
        if(hasAnswer) {
            map.put("hasAnswer","1");
        }
        List<ExamQuestionVo> list = examQuestionMapper.selectQuestionVoByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public PageInfo<Long> getQuestionMarkPage(ExamQuestionMarkPageReq examQuestionMarkPageReq) {
        Integer page = examQuestionMarkPageReq.getPage();
        Integer size = examQuestionMarkPageReq.getSize();
        Map<String, Object> map = EntityUtil.entityToMap(examQuestionMarkPageReq);
        PageInfo<Long> PageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> {
            examQuestionMapper.selectQUestionIdsByMap(map);
        });
        return PageInfo;
    }

    @Override
    public String getQuestionDescById(Long questionId) {
        return examQuestionMapper.selectQuestionDescById(questionId);
    }
}
