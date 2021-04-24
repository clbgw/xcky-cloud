package com.xcky.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.ExamPaperUserMapper;
import com.xcky.model.entity.ExamPaperUser;
import com.xcky.model.req.ExamPaperUserPageReq;
import com.xcky.model.vo.ExamPaperUserPageVo;
import com.xcky.service.ExamPaperUserService;
import com.xcky.util.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户考试服务实现类
 *
 * @author lbchen
 */
@Service
public class ExamPaperUserServiceImpl implements ExamPaperUserService {
    @Autowired
    private ExamPaperUserMapper examPaperUserMapper;

    @Override
    public Integer savePaperUser(ExamPaperUser examPaperUser) {
        return examPaperUserMapper.insertExamPaperUser(examPaperUser);
    }

    @Override
    public Long getPreparId(Long paperUserId, Long userId) {
        return examPaperUserMapper.selectPaperId(paperUserId,userId);
    }

    @Override
    public ExamPaperUser getPaperUserInfoById(Long paperUserId) {
        return examPaperUserMapper.selectPaperUserById(paperUserId);
    }

    @Override
    public PageInfo<ExamPaperUserPageVo> getPaperUserPageByReq(ExamPaperUserPageReq examPaperUserPageReq) {
        Integer page = examPaperUserPageReq.getPage();
        Integer size = examPaperUserPageReq.getSize();
        Map<String,Object> map = EntityUtil.entityToMap(examPaperUserPageReq);
        PageInfo<ExamPaperUserPageVo> pageInfo = PageHelper.startPage(page,size).doSelectPageInfo(()->{
            examPaperUserMapper.selectPaperUserPageVoByMap(map);
        });
        return pageInfo;
    }
}
