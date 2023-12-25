package com.linmo.oj.controller;

import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.questionsubmit.QuestionSubmit;
import com.linmo.oj.model.questionsubmit.dto.QuestionSubmitAddDto;
import com.linmo.oj.model.questionsubmit.dto.QuestionSubmitQueryDto;
import com.linmo.oj.model.questionsubmit.vo.QuestionSubmitVo;
import com.linmo.oj.model.questionsubmit.vo.SubmitSummaryVo;
import com.linmo.oj.service.QuestionSubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-22 09:59
 */
@RestController
@Api(tags = "题目提交接口")
@RequestMapping("/question_submit")
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @ApiOperation(value = "提交题目")
    @PostMapping(value = "/submit")
    public BaseResponse<QuestionSubmit> doQuestionSubmit(@Validated @RequestBody QuestionSubmitAddDto questionSubmitAddDto) {
        return ResultUtils.success(questionSubmitService.doQuestionSubmit(questionSubmitAddDto));
    }

    @ApiOperation(value = "根据id获取提交记录")
    @GetMapping(value = "/queryById")
    public BaseResponse<QuestionSubmitVo> queryQuestionSubmitById(@RequestParam("id") Long id) {
        return ResultUtils.success(questionSubmitService.queryById(id));
    }

    @ApiOperation(value = "分页查询题目提交历史")
    @PostMapping(value = "/queryList")
    public BaseResponse<PageResult<QuestionSubmitVo>> queryListQuestion(@Validated @RequestBody QuestionSubmitQueryDto questionSubmitQueryDto) {
        return ResultUtils.success(questionSubmitService.queryByPage(questionSubmitQueryDto));
    }

    @ApiOperation(value = "获取提交概况")
    @GetMapping(value = "/summary")
    public BaseResponse<SubmitSummaryVo> getSubmitSummary() {
        return ResultUtils.success(questionSubmitService.getSubmitSummary());
    }

}
