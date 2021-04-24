package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xcky.enums.ExamModeEnum;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.model.entity.ExamAnswerUser;
import com.xcky.model.entity.ExamPaper;
import com.xcky.model.entity.ExamPaperUser;
import com.xcky.model.entity.ExamQuestionUser;
import com.xcky.model.req.ExamPaperPageReq;
import com.xcky.model.req.ExamPaperQuestionDetailReq;
import com.xcky.model.req.ExamPaperQuestionPageReq;
import com.xcky.model.req.ExamPaperUserDetailReq;
import com.xcky.model.req.ExamPaperUserPageReq;
import com.xcky.model.req.ExamPaperUserSaveReq;
import com.xcky.model.req.ExamQuestionAnswerReq;
import com.xcky.model.req.ExamQuestionCollectReq;
import com.xcky.model.req.ExamQuestionDescReq;
import com.xcky.model.req.ExamQuestionErrorReq;
import com.xcky.model.req.ExamQuestionMarkPageReq;
import com.xcky.model.req.ExamQuestionPageReq;
import com.xcky.model.req.ExamQuestionPaperAnswerReq;
import com.xcky.model.req.ExamQuestionPaperAnswerSubReq;
import com.xcky.model.req.ExamQuestionSingleAnswerReq;
import com.xcky.model.req.ExamQuestionStarReq;
import com.xcky.model.resp.R;
import com.xcky.model.vo.ExamAnswerVo;
import com.xcky.model.vo.ExamPaperPageVo;
import com.xcky.model.vo.ExamPaperQuestionPageVo;
import com.xcky.model.vo.ExamPaperUserPageVo;
import com.xcky.model.vo.ExamQuestionVo;
import com.xcky.service.ExamAnswerService;
import com.xcky.service.ExamAnswerUserService;
import com.xcky.service.ExamPaperQuestionService;
import com.xcky.service.ExamPaperService;
import com.xcky.service.ExamPaperUserService;
import com.xcky.service.ExamQuestionService;
import com.xcky.service.ExamQuestionUserService;
import com.xcky.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 问题控制器
 *
 * @author lbchen
 */
@Slf4j
@RestController
public class ExamController {
    @Autowired
    private ExamQuestionService examQuestionService;
    @Autowired
    private ExamQuestionUserService examQuestionUserService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private ExamPaperUserService examPaperUserService;
    @Autowired
    private ExamPaperQuestionService examPaperQuestionService;
    @Autowired
    private ExamAnswerUserService examAnswerUserService;
    @Autowired
    private ExamAnswerService examAnswerService;

    /**
     * 查询问题分页列表
     *
     * @return 问题分页列表
     */
    @PostMapping("/exam/question/getPage")
    public R getQuestionPage(@RequestBody ExamQuestionPageReq examQuestionPageReq) {
        PageInfo<ExamQuestionVo> pageInfo = examQuestionService.getQuestionVoPageByReq(examQuestionPageReq);
        return ResponseUtil.ok(pageInfo);
    }

    /**
     * 01.查看试卷列表
     */
    @PostMapping("/exam/paper/getPage")
    public R getPaperPage(@RequestBody ExamPaperPageReq examPaperPageReq) {
        PageInfo<ExamPaperPageVo> pageInfo = examPaperService.getPaperVoPageByReq(examPaperPageReq);
        return ResponseUtil.ok(pageInfo);
    }

    /**
     * 01-1.开始试卷考试
     */
    @PostMapping("/exam/paper/savePageUser")
    public R savePageUser(@RequestBody ExamPaperUserSaveReq examPaperUserSaveReq) {
        Long paperId = examPaperUserSaveReq.getPaperId();
        // 查询试卷详情
        ExamPaper examPaper = examPaperService.getPaperVoByPaperId(paperId);
        Date nowDate = new Date();

        judgePaper(examPaper, nowDate);

        Long userId = examPaperUserSaveReq.getUserId();
        ExamPaperUser examPaperUser = new ExamPaperUser();
        examPaperUser.setPaperId(paperId);
        examPaperUser.setUserId(userId);
        examPaperUser.setScore(0);
        examPaperUser.setCreateTime(nowDate);
        examPaperUser.setUpdateTime(nowDate);
        examPaperUser.setIsPass("0");
        examPaperUser.setTotalScore(examPaper.getTotalScore());


        //TODO 查询最近时间段内用户是否参与了该试卷的考试

        // 保存用户考试记录
        Integer saveResult = examPaperUserService.savePaperUser(examPaperUser);
        if (null == saveResult || saveResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_ERROR);
        }

        // 返回当前时间，方便计算考试剩余时间
        Map<String, Object> resultMap = new HashMap<>(8);
        // 考试开始时间
        resultMap.put("nowTime", nowDate);
        // 考试结束时间
        resultMap.put("endTIme", new Date(nowDate.getTime() + 60000 * examPaper.getDurationMinus()));
        // 考试记录主键ID
        resultMap.put("examPaperUserId", examPaperUser.getId());
        // 试卷详情
        resultMap.put("paper", examPaper);
        return ResponseUtil.ok(resultMap);
    }

    /**
     * 02.查看试卷信息及题目
     */
    @PostMapping("/exam/paper/getQuestionPage")
    public R getQuestionPage(@RequestBody ExamPaperQuestionPageReq examPaperQuestionPageReq) {
        Long userId = examPaperQuestionPageReq.getUserId();
        Long paperUserId = examPaperQuestionPageReq.getPaperUserId();

        Long dbPaperId = examPaperUserService.getPreparId(paperUserId, userId);
        if (null == dbPaperId) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long paperId = examPaperQuestionPageReq.getPaperId();
        // 鉴别入参正确性(输入的paperId 和其他参数关联的paperId要能够相等)
        if (!paperId.equals(dbPaperId)) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }

        ExamPaper examPaper = examPaperService.getPaperVoByPaperId(paperId);
        Date nowDate = new Date();

        judgePaper(examPaper, nowDate);

        PageInfo<ExamPaperQuestionPageVo> pageInfo = examPaperQuestionService.getPaperQuestionVoPageByReq(examPaperQuestionPageReq);
        return ResponseUtil.ok(pageInfo);
    }

    /**
     * 03.查看问题详情(试卷/单题)
     */
    @PostMapping("/exam/question/getDetail")
    public R getDetail(@RequestBody ExamPaperQuestionDetailReq examPaperQuestionDetailReq) {
        Map<String, Object> resultMap = new HashMap<>(4);
        Long questionId = examPaperQuestionDetailReq.getQuestionId();
        ExamQuestionVo examQuestionVo = examQuestionService.getQuestionVoByQuestionId(questionId,false);
        Integer examMode = examPaperQuestionDetailReq.getExamMode();
        if (examMode.equals(ExamModeEnum.REVIEW.getCode())) {
            // 查询个人的问题标记
            Long userId = examPaperQuestionDetailReq.getUserId();
            ExamQuestionUser examQuestionUser = examQuestionUserService.getExamQuestionUserOne(userId, questionId);
            resultMap.put("qurstionUser", examQuestionUser);
        }
        resultMap.put("question", examQuestionVo);
        return ResponseUtil.ok(resultMap);
    }

    /**
     * 04.按试卷提交答案
     */
    @PostMapping("/exam/question/checkPaperAnswer")
    public R checkPaperAnswer(@RequestBody ExamQuestionPaperAnswerReq examQuestionPaperAnswerReq) {
        Date nowDate = new Date();

        Long userId = examQuestionPaperAnswerReq.getUserId();
        Long paperUserId = examQuestionPaperAnswerReq.getPaperUserId();

        ExamPaperUser examPaperUser = examPaperUserService.getPaperUserInfoById(paperUserId);
        if (null == examPaperUser || null == examPaperUser.getPaperId()) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }
        Long dbPaperId = examPaperUser.getPaperId();
        Long paperId = examQuestionPaperAnswerReq.getPaperId();
        // 鉴别入参正确性(输入的paperId 和其他参数关联的paperId要能够相等)
        if (!paperId.equals(dbPaperId) || !userId.equals(examPaperUser.getUserId())) {
            throw new BizException(ResponseEnum.PARAM_ERROR, null);
        }

        // 查询试卷的详情
        ExamPaper examPaper = examPaperService.getPaperVoByPaperId(paperId);
        judgePaper(examPaper, nowDate);

        // 判断试卷是否超时提交
        Date beginExamTime = examPaperUser.getCreateTime();
        Integer examDurationMinus = examPaper.getDurationMinus();
        if (nowDate.after(new Date(beginExamTime.getTime() + 60000 * examDurationMinus))) {
            // 考试已经结束
            throw new BizException(ResponseEnum.PAPER_ANSWER_LIMIT_TIME, null);
        }

        // 查询试卷的所有问题及答案
        List<ExamQuestionPaperAnswerSubReq> answers = examQuestionPaperAnswerReq.getAnswers();
        List<ExamQuestionPaperAnswerSubReq> rightAnswers = examAnswerService.getQuestionRightAnswersByPaperId(paperId);

        Map<Long, ExamQuestionPaperAnswerSubReq> rightAnswerMap = new HashMap<>(32);
        for (ExamQuestionPaperAnswerSubReq rightAnswer : rightAnswers) {
            if (null == rightAnswer) {
                continue;
            }
            rightAnswerMap.put(rightAnswer.getQuestionId(), rightAnswer);
        }

        Integer scoreResult = 0;
        ExamAnswerUser examAnswerUser;
        for (ExamQuestionPaperAnswerSubReq answer : answers) {
            if (null == answer) {
                continue;
            }
            ExamQuestionPaperAnswerSubReq temp = rightAnswerMap.get(answer.getQuestionId());
            String checkResult = checkAnswer(temp.getAnswerIds(), answer.getAnswerIds());

            // 记录用户答题记录可以异步处理
            examAnswerUser = new ExamAnswerUser();
            examAnswerUser.setCreateTime(nowDate);
            examAnswerUser.setIsRight(checkResult);
            examAnswerUser.setQuestionId(answer.getQuestionId());
            examAnswerUser.setSubmitAnswer(JSONObject.toJSONString(answer.getAnswerIds()));
            examAnswerUser.setUserId(userId);
            examAnswerUser.setPaperId(paperId);
            Integer saveResult = examAnswerUserService.saveExamAnswerUser(examAnswerUser);
            if (null == saveResult || saveResult < 1) {
                log.error("新增用户答题记录失败:" + JSONObject.toJSONString(examAnswerUser));
            }

            if ("0".equals(checkResult)) {
                continue;
            }
            scoreResult += (new BigDecimal(temp.getScore())
                    .divide(new BigDecimal(checkResult))
                    .setScale(0, RoundingMode.HALF_UP)).intValue();
        }

        String isPass = scoreResult >= examPaper.getPassScore() ? "1" : "0";

        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("paper", examPaper);
        resultMap.put("score", scoreResult);
        resultMap.put("isPass", isPass);
        return ResponseUtil.ok(resultMap);
    }


    /**
     * 05.单题提交答案
     */
    @PostMapping("/exam/question/checkOneAnswer")
    public R checkOneAnswer(@RequestBody ExamQuestionSingleAnswerReq examQuestionSingleAnswerReq) {
        Long questionId = examQuestionSingleAnswerReq.getQuestionId();
        Long userId = examQuestionSingleAnswerReq.getUserId();
        Long paperId = examQuestionSingleAnswerReq.getPaperId();
        // 查询问题的正确答案
        Set<Long> rightAnswers = examAnswerService.getQuestionRightAnswer(questionId, "1", "1");
        String isRight = "0";
        Set<Long> submitAnswer = examQuestionSingleAnswerReq.getAnswerIds();
        if (null != rightAnswers && rightAnswers.size() > 0) {
            // 判断正误
            isRight = checkAnswer(rightAnswers, submitAnswer);
        } else {
            log.error("题目【{}】未设置答案:", questionId);
        }
        // 记录用户的答题记录
        Date nowDate = new Date();
        ExamAnswerUser examAnswerUser = new ExamAnswerUser();
        examAnswerUser.setCreateTime(nowDate);
        examAnswerUser.setIsRight(isRight);
        examAnswerUser.setQuestionId(questionId);
        examAnswerUser.setSubmitAnswer(JSONObject.toJSONString(submitAnswer));
        examAnswerUser.setUserId(userId);
        examAnswerUser.setPaperId(paperId);
        Integer saveResult = examAnswerUserService.saveExamAnswerUser(examAnswerUser);
        if (null == saveResult || saveResult < 1) {
            log.error("新增用户答题记录失败:" + JSONObject.toJSONString(examAnswerUser));
            return ResponseUtil.fail(ResponseEnum.INSERT_ERROR);
        }
        return ResponseUtil.ok(isRight);
    }


    /**
     * 06.查看单题题目答案
     */
    @PostMapping("/exam/question/getAnswer")
    public R getQuestionAnswer(@RequestBody ExamQuestionAnswerReq examQuestionAnswerReq) {
        Long questionId = examQuestionAnswerReq.getQuestionId();
        // 获取问题的所有答案
        Map<String, Object> map = new HashMap<>(4);
        map.put("questionId", questionId);
        map.put("status", "1");
        List<ExamAnswerVo> answerVos = examAnswerService.getAnswerVoByMap(map);
        return ResponseUtil.ok(answerVos);
    }

    /**
     * 07.查看题目解析
     */
    @PostMapping("/exam/question/getAnswerDesc")
    public R getAnswerDesc(@RequestBody ExamQuestionDescReq examQuestionDescReq) {
        Long questionId = examQuestionDescReq.getQuestionId();
        String desc = examQuestionService.getQuestionDescById(questionId);
        return ResponseUtil.ok(desc);
    }

    /**
     * 08.查看试卷答题结果列表
     */
    @PostMapping("/exam/paperUser/getPage")
    public R getPaperUserPage(@RequestBody ExamPaperUserPageReq examPaperUserPageReq) {
        PageInfo<ExamPaperUserPageVo> pageInfo = examPaperUserService.getPaperUserPageByReq(examPaperUserPageReq);
        return ResponseUtil.ok(pageInfo);
    }

    /**
     * TODO 09.查看试卷答题结果详情
     */
    @PostMapping("/exam/paperUser/getDetail")
    public R getPaperUserDetail(@RequestBody ExamPaperUserDetailReq examPaperUserDetailReq) {

        return ResponseUtil.ok();
    }

    /**
     * TODO 10.查看历史答题记录列表
     */
    /**
     * TODO 11.查看历史答题记录详情
     */

    /**
     * 12.问题打收藏
     */
    @PostMapping("/exam/question/toCllect")
    public R toCllect(@RequestBody ExamQuestionCollectReq examQuestionCollectReq) {
        Date nowDate = new Date();
        ExamQuestionUser examQuestionUser = new ExamQuestionUser();
        examQuestionUser.setUpdateTime(nowDate);
        // 收藏
        examQuestionUser.setIsCollect(examQuestionCollectReq.getIsCollect());
        examQuestionUser.setQuestionId(examQuestionCollectReq.getQuestionId());
        examQuestionUser.setUserId(examQuestionCollectReq.getUserId());
        Integer updateResult = examQuestionUserService.updateExamQuestionUser(examQuestionUser);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok();
    }

    /**
     * 13.问题打星标
     */
    @PostMapping("/exam/question/toStar")
    public R toStar(@RequestBody ExamQuestionStarReq examQuestionStarReq) {
        Date nowDate = new Date();
        ExamQuestionUser examQuestionUser = new ExamQuestionUser();
        examQuestionUser.setUpdateTime(nowDate);
        // 打星
        examQuestionUser.setStarMark(examQuestionStarReq.getStarMark());
        examQuestionUser.setQuestionId(examQuestionStarReq.getQuestionId());
        examQuestionUser.setUserId(examQuestionStarReq.getUserId());
        Integer updateResult = examQuestionUserService.updateExamQuestionUser(examQuestionUser);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok();
    }

    /**
     * 14.问题记错题
     */
    @PostMapping("/exam/question/toError")
    public R toError(@RequestBody ExamQuestionErrorReq examQuestionErrorReq) {
        Date nowDate = new Date();
        ExamQuestionUser examQuestionUser = new ExamQuestionUser();
        examQuestionUser.setUpdateTime(nowDate);
        // 记录错题
        examQuestionUser.setIsError(examQuestionErrorReq.getIsError());
        examQuestionUser.setQuestionId(examQuestionErrorReq.getQuestionId());
        examQuestionUser.setUserId(examQuestionErrorReq.getUserId());
        Integer updateResult = examQuestionUserService.updateExamQuestionUser(examQuestionUser);
        if (null == updateResult || updateResult < 1) {
            return ResponseUtil.fail(ResponseEnum.INSERT_OR_UPDATE_ERROR);
        }
        return ResponseUtil.ok();
    }

    /**
     * 15.查看错题本/收藏本/星标本
     */
    @PostMapping("/exam/question/getMarkPage")
    public R getMarkPage(@RequestBody ExamQuestionMarkPageReq examQuestionMarkPageReq) {
        PageInfo<Long> pageInfo = examQuestionService.getQuestionMarkPage(examQuestionMarkPageReq);
        return ResponseUtil.ok(pageInfo);
    }

    /**
     * 如果提交为空的答案，直接为【0】-错误<br>
     * 如果提交的答案是正确答案的子集(不是真子集)，则为【2】-部分答对
     * 如果提交的答案是正确答案的真子集，则为【1】-全对
     *
     * @param answer       正确答案
     * @param submitAnswer 提交的答案
     * @return 判断结果(0 - 错误, 1 - 全对, 2 - 部分答对)
     */
    private String checkAnswer(Set<Long> answer, Set<Long> submitAnswer) {
        String isRight = "0";
        if (null == submitAnswer || submitAnswer.size() < 1) {
            return isRight;
        }
        Map<Long, Long> answerMap = new HashMap<>();
        for (Long a : answer) {
            answerMap.put(a, a);
        }
        for (Long s : submitAnswer) {
            // 错误
            if (answerMap.get(s) == null) {
                return isRight;
            }
        }
        // 全对
        if (answerMap.keySet().size() == submitAnswer.size()) {
            return "1";
        }
        //部分答对
        return "2";
    }

    /**
     * 校验试卷的状态和时间
     *
     * @param examPaper 试卷信息
     * @param nowDate   当前时间
     */
    private void judgePaper(ExamPaper examPaper, Date nowDate) {
        if (null == examPaper) {
            throw new BizException(ResponseEnum.PAPER_NOT_EXIST, null);
        }
        // 试卷状态未启用
        if (!examPaper.getStatus().equals("1")) {
            throw new BizException(ResponseEnum.PAPER_NOT_USE, null);
        }
        // 考试时间未到
        if (examPaper.getBeginTime().after(nowDate)) {
            throw new BizException(ResponseEnum.PAPER_NOT_BEGIN, null);
        }
        // 考试时间已过
        if (examPaper.getEndTime().before(nowDate)) {
            throw new BizException(ResponseEnum.PAPER_HAD_END, null);
        }
    }
}
