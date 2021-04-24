package com.xcky.service.impl;

import com.xcky.mapper.ExamQuestionUserMapper;
import com.xcky.model.entity.ExamQuestionUser;
import com.xcky.service.ExamQuestionUserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 问题与用户管理关系服务实现类
 *
 * @author lbchen
 */
@Service
public class ExamQuestionUserServiceImpl implements ExamQuestionUserService {
    @Autowired
    private ExamQuestionUserMapper examQuestionUserMapper;

    @Override
    public ExamQuestionUser getExamQuestionUserOne(Long userId, Long questionId) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("userId", userId);
        map.put("questionId", questionId);
        List<ExamQuestionUser> list = examQuestionUserMapper.selectExamQuestionUserByMap(map);
        if (null == list || list.size() < 1) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer updateExamQuestionUser(ExamQuestionUser examQuestionUser) {
        ExamQuestionUser temp = getExamQuestionUserOne(examQuestionUser.getUserId(),examQuestionUser.getQuestionId());
        if(null == temp) {
            // 新增
            examQuestionUser.setCreateTime(examQuestionUser.getUpdateTime());
            return examQuestionUserMapper.insertExamQuestionUser(examQuestionUser);
        } else {
            // 更新
            return examQuestionUserMapper.updateExamQuestionUser(examQuestionUser);
        }
    }
}
