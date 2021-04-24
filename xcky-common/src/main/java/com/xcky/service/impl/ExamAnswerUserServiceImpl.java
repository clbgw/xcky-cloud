package com.xcky.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.log.Log;
import com.xcky.mapper.ExamAnswerUserMapper;
import com.xcky.mapper.ExamQuestionUserMapper;
import com.xcky.model.entity.ExamAnswerUser;
import com.xcky.model.entity.ExamQuestionUser;
import com.xcky.service.ExamAnswerUserService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javassist.expr.NewArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户答题服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class ExamAnswerUserServiceImpl implements ExamAnswerUserService {
    @Autowired
    private ExamAnswerUserMapper examAnswerUserMapper;
    @Autowired
    private ExamQuestionUserMapper examQuestionUserMapper;

    @Override
    @Transactional
    public Integer saveExamAnswerUser(ExamAnswerUser examAnswerUser) {
        // 查看用户问题关联关系表,
        Map<String, Object> map = new HashMap<>();
        map.put("userId", examAnswerUser.getUserId());
        map.put("questionId", examAnswerUser.getQuestionId());
        List<ExamQuestionUser> list = examQuestionUserMapper.selectExamQuestionUserByMap(map);

        Date nowDate = new Date();
        Integer rightCount = examAnswerUser.getIsRight().equals("0") ? 0 : 1;
        ExamQuestionUser examQuestionUser;
        if (null == list || list.size() < 1) {
            examQuestionUser = new ExamQuestionUser();
            examQuestionUser.setCreateTime(nowDate);
            examQuestionUser.setUserId(examAnswerUser.getUserId());
            examQuestionUser.setQuestionId(examAnswerUser.getQuestionId());
            examQuestionUser.setUpdateTime(nowDate);
            examQuestionUser.setRecentRightCount(rightCount);
            Integer saveResult = examQuestionUserMapper.insertExamQuestionUser(examQuestionUser);
            if (null == saveResult || saveResult < 1) {
                log.error("新增用户与问题关联关系失败:" + JSONObject.toJSONString(examQuestionUser));
            }
        } else {
            examQuestionUser = list.get(0);
            examQuestionUser.setUserId(examAnswerUser.getUserId());
            examQuestionUser.setQuestionId(examAnswerUser.getQuestionId());
            examQuestionUser.setUpdateTime(nowDate);
            if (0 == rightCount) {
                examQuestionUser.setRecentRightCount(0);
            } else {
                examQuestionUser.setRecentRightCount(examQuestionUser.getRecentRightCount() + 1);
            }
            Integer updateResult = examQuestionUserMapper.updateExamQuestionUser(examQuestionUser);
            if (null == updateResult || updateResult < 1) {
                log.error("更新用户与问题关联关系失败:" + JSONObject.toJSONString(examQuestionUser));
            }
        }

        return examAnswerUserMapper.insertAnswerUser(examAnswerUser);
    }
}
