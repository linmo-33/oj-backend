package com.linmo.oj.service;

import com.linmo.oj.model.questionsubmit.QuestionSubmit;

/**
 * 判题服务，抽象出微服务
 *
 * @author ljl
 * @since 2023-12-22 14:04
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmit
     * @return 判题结果
     */
    QuestionSubmit doJudge(QuestionSubmit questionSubmit);
}
