package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.question.dto.QuestionAddDto;
import com.linmo.oj.model.question.dto.QuestionEditDto;
import com.linmo.oj.model.question.dto.QuestionQueryDto;
import com.linmo.oj.model.question.vo.QuestionVo;
import com.linmo.oj.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 15:21
 */
@RestController
@Api(tags = "题目管理接口")
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;


    @ApiOperation(value = "新增题目")
    @PostMapping(value = "/create")
    @LogRecord(value = "新增题目")
    @SaCheckPermission(value = "question.create", orRole = "admin")
    public BaseResponse<Boolean> createQuestion(@Validated @RequestBody QuestionAddDto questionAddDto) {
        return ResultUtils.success(questionService.create(questionAddDto));
    }

    @ApiOperation(value = "修改题目")
    @PostMapping(value = "/update")
    @LogRecord(value = "修改题目")
    @SaCheckPermission(value = "question.update", orRole = "admin")
    public BaseResponse<Boolean> updateQuestion(@Validated @RequestBody QuestionEditDto questionEditDto) {
        return ResultUtils.success(questionService.update(questionEditDto));
    }

    @ApiOperation(value = "删除题目")
    @GetMapping(value = "/delete")
    @LogRecord(value = "删除题目")
    @SaCheckPermission(value = "question.delete", orRole = "admin")
    public BaseResponse<Boolean> deleteQuestion(@RequestParam("id") Long id) {
        return ResultUtils.success(questionService.delete(id));
    }

    @ApiOperation(value = "根据id获取题目")
    @GetMapping(value = "/queryById")
    public BaseResponse<QuestionVo> queryQuestionById(@RequestParam("id") Long id) {
        return ResultUtils.success(questionService.queryById(id));
    }

    @ApiOperation(value = "分页查询题目")
    @PostMapping(value = "/queryList")
    public BaseResponse<PageResult<QuestionVo>> queryListQuestion(@Validated @RequestBody QuestionQueryDto questionQueryDto) {
        return ResultUtils.success(questionService.queryByPage(questionQueryDto));
    }

    @ApiOperation(value = "获取所有题目标签")
    @GetMapping("/tags")
    public BaseResponse<List<String>> getQuestionTags() {
        List<String> tags = questionService.getQuestionTags();
        return ResultUtils.success(tags);
    }


}
