package com.linmo.oj.service.impl;

import com.linmo.oj.codesandbox.CodeSandbox;
import com.linmo.oj.codesandbox.CodeSandboxFactory;
import com.linmo.oj.codesandbox.model.ExecuteCodeRequest;
import com.linmo.oj.codesandbox.model.ExecuteCodeResponse;
import com.linmo.oj.model.enums.ExecuteCodeStatusEnum;
import com.linmo.oj.model.questionsubmit.dto.QuestionRunRequest;
import com.linmo.oj.model.questionsubmit.vo.QuestionRunResult;
import com.linmo.oj.service.QuestionRunService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-25 10:53
 */
@Service
public class QuestionRunServiceImpl implements QuestionRunService {

    @Value("${codesandbox.type}")
    private String type;
    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    /**
     * 执行代码
     * @param questionRunRequest 代码执行请求
     * @return QuestionRunResult
     */
    @Override
    public QuestionRunResult doQuestionRun(QuestionRunRequest questionRunRequest) {
        String code = questionRunRequest.getCode();
        String language = questionRunRequest.getLanguage();
        List<String> inputList = Collections.singletonList(questionRunRequest.getInput());

        CodeSandbox codeSandbox = codeSandboxFactory.newInstance(type);
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse response = codeSandbox.executeCode(executeCodeRequest);

        return getQuestionRunVo(questionRunRequest.getInput(), response);
    }

    /**
     * 获取执行结果
     * @param input 输入
     * @param response 执行结果
     * @return QuestionRunResult
     */
    private static QuestionRunResult getQuestionRunVo(String input, ExecuteCodeResponse response) {
        QuestionRunResult questionRunResult = new QuestionRunResult();
        questionRunResult.setInput(input);
        //执行成功
        if(response.getCode().equals(ExecuteCodeStatusEnum.SUCCESS.getValue())){
            questionRunResult.setCode(ExecuteCodeStatusEnum.SUCCESS.getValue());
            questionRunResult.setOutput(response.getResults().get(0).getOutput());
        } else if(response.getCode().equals(ExecuteCodeStatusEnum.RUN_FAILED.getValue())){
            questionRunResult.setCode(ExecuteCodeStatusEnum.RUN_FAILED.getValue());
            questionRunResult.setOutput(response.getMsg());
        } else if(response.getCode().equals(ExecuteCodeStatusEnum.COMPILE_FAILED.getValue())){
            questionRunResult.setCode(ExecuteCodeStatusEnum.COMPILE_FAILED.getValue());
            questionRunResult.setOutput(response.getMsg());
        }
        return questionRunResult;
    }
}
