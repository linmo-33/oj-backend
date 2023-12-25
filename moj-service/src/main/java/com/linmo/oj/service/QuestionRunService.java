package com.linmo.oj.service;

import com.linmo.oj.model.questionsubmit.dto.QuestionRunRequest;
import com.linmo.oj.model.questionsubmit.vo.QuestionRunResult;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-25 10:52
 */
public interface QuestionRunService {

    QuestionRunResult doQuestionRun(QuestionRunRequest questionRunRequest);
}
