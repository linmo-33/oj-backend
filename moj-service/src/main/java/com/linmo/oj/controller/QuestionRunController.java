package com.linmo.oj.controller;

import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.questionsubmit.dto.QuestionRunRequest;
import com.linmo.oj.model.questionsubmit.vo.QuestionRunResult;
import com.linmo.oj.service.QuestionRunService;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "题目运行接口")
@RequestMapping("/question_run")
public class QuestionRunController {
    
    @Resource
    private QuestionRunService questionRunService;

    @PostMapping("/execute")
    public BaseResponse<QuestionRunResult> doQuestionRun(@Validated @RequestBody QuestionRunRequest questionRunRequest) {
        return ResultUtils.success(questionRunService.doQuestionRun(questionRunRequest));
    }
}
