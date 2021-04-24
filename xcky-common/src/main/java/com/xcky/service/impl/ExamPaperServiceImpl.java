package com.xcky.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xcky.mapper.ExamPaperMapper;
import com.xcky.model.entity.ExamPaper;
import com.xcky.model.req.ExamPaperPageReq;
import com.xcky.model.vo.ExamPaperPageVo;
import com.xcky.service.ExamPaperService;
import com.xcky.util.EntityUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 试卷服务实现类
 *
 * @author lbchen
 */
@Service
public class ExamPaperServiceImpl implements ExamPaperService {
    @Autowired
    private ExamPaperMapper examPaperMapper;
    @Override
    public PageInfo<ExamPaperPageVo> getPaperVoPageByReq(ExamPaperPageReq examPaperPageReq) {
        Integer page = examPaperPageReq.getPage();
        Integer size = examPaperPageReq.getSize();
        Map<String,Object> map = EntityUtil.entityToMap(examPaperPageReq);
        PageInfo<ExamPaperPageVo> pageInfo = PageHelper.startPage(page,size).doSelectPageInfo(()->{
            examPaperMapper.selectPaperPageVosByMap(map);
        });
        return pageInfo;
    }

    @Override
    public ExamPaper getPaperVoByPaperId(Long paperId) {
        return examPaperMapper.selectPaperByPaperId(paperId);
    }
}
