package com.linmo.oj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.linmo.oj.codesandbox.CodeSandbox;
import com.linmo.oj.codesandbox.CodeSandboxFactory;
import com.linmo.oj.codesandbox.model.ExecuteCodeRequest;
import com.linmo.oj.codesandbox.model.ExecuteCodeResponse;
import com.linmo.oj.codesandbox.model.ExecuteResult;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.mapper.QuestionMapper;
import com.linmo.oj.mapper.QuestionSubmitMapper;
import com.linmo.oj.model.enums.ExecuteCodeStatusEnum;
import com.linmo.oj.model.enums.JudgeInfoEnum;
import com.linmo.oj.model.enums.QuestionSubmitStatusEnum;
import com.linmo.oj.model.question.Question;
import com.linmo.oj.model.question.dto.JudgeCase;
import com.linmo.oj.model.question.dto.JudgeConfig;
import com.linmo.oj.model.questionsubmit.QuestionSubmit;
import com.linmo.oj.model.questionsubmit.vo.JudgeInfo;
import com.linmo.oj.service.JudgeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题服务实现类
 *
 * @author ljl
 * @since 2023-12-22 14:05
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionSubmitMapper questionSubmitMapper;

//    @Resource
//    private JudgeManager judgeManager;

    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    @Value("${codesandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(QuestionSubmit questionSubmit) {

        // 1、获取对应的question
        Question question = questionMapper.selectOne(new QueryWrapper<Question>()
                .select(Question.class, item -> !item.getColumn().equals("content") && !item.getColumn().equals("answer"))
                .lambda().eq(Question::getId, questionSubmit.getQuestionId()));
        if(question == null){
            throw new BusinessException("题目不存在");
        }

        // 2、更改判题（题目提交）的状态为"判题中"，防止重复执行
        QuestionSubmit updateSubmit = new QuestionSubmit();
        updateSubmit.setId(questionSubmit.getId());
        updateSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        int update = questionSubmitMapper.updateById(updateSubmit);
        if (update == 0) {
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "题目状态更新失败");
        }

        // 3、调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = codeSandboxFactory.newInstance(type);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse response = codeSandbox.executeCode(executeCodeRequest);

        // 4、根据沙箱的执行结果，设置题目的判题状态和信息

        JudgeInfo judgeInfo = new JudgeInfo();
        int total = judgeCaseList.size();
        judgeInfo.setTotal(total);
        //执行成功
        if(response.getCode().equals(ExecuteCodeStatusEnum.SUCCESS.getValue())){
            //期望输出
            List<String> expectedOutput = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
            //测试用例详细信息
            List<ExecuteResult> results = response.getResults();
            //实际输出
            List<String> output = results.stream().map(ExecuteResult::getOutput).collect(Collectors.toList());
            //判题配置
            JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);

            //设置通过的测试用例
            int pass = 0;
            //设置最大实行时间
            long maxTime = Long.MIN_VALUE;
            for (int i = 0; i < total; i++) {
                //判断执行时间
                Long time = results.get(i).getTime();
                if(time > maxTime){
                    maxTime = time;
                }
                if(expectedOutput.get(i).equals(output.get(i))){
                    //超时
                    if(maxTime > judgeConfig.getTimeLimit()){
                        judgeInfo.setTime(maxTime);
                        judgeInfo.setPass(pass);
                        judgeInfo.setStatus(JudgeInfoEnum.TIME_LIMIT_EXCEEDED.getValue());
                        judgeInfo.setMessage(JudgeInfoEnum.TIME_LIMIT_EXCEEDED.getText());
                        break;
                    } else {
                        pass++;
                    }
                } else {
                    //遇到了一个没通过的
                    judgeInfo.setPass(pass);
                    judgeInfo.setTime(maxTime);
                    judgeInfo.setStatus(JudgeInfoEnum.WRONG_ANSWER.getValue());
                    judgeInfo.setMessage(JudgeInfoEnum.WRONG_ANSWER.getText());
                    //设置输出和预期输出信息
                    judgeInfo.setInput(inputList.get(i));
                    judgeInfo.setOutput(output.get(i));
                    judgeInfo.setExpectedOutput(expectedOutput.get(i));
                    break;
                }
            }
            if(pass == total){
                judgeInfo.setPass(total);
                judgeInfo.setTime(maxTime);
                judgeInfo.setStatus(JudgeInfoEnum.ACCEPTED.getValue());
                judgeInfo.setMessage(JudgeInfoEnum.ACCEPTED.getText());
            }
        } else if(response.getCode().equals(ExecuteCodeStatusEnum.RUN_FAILED.getValue())){
            judgeInfo.setPass(0);
            judgeInfo.setStatus(JudgeInfoEnum.RUNTIME_ERROR.getValue());
            judgeInfo.setMessage(JudgeInfoEnum.RUNTIME_ERROR.getText() + response.getMsg());
        } else if(response.getCode().equals(ExecuteCodeStatusEnum.COMPILE_FAILED.getValue())){
            judgeInfo.setPass(0);
            judgeInfo.setStatus(JudgeInfoEnum.COMPILE_ERROR.getValue());
            judgeInfo.setMessage(JudgeInfoEnum.COMPILE_ERROR.getText() + response.getMsg());
        }

        // 5、修改数据库中的判题结果
        boolean judgeResult = judgeInfo.getStatus().equals(JudgeInfoEnum.ACCEPTED.getValue());

        updateSubmit.setStatus(judgeResult ?
                QuestionSubmitStatusEnum.SUCCEED.getValue() :
                QuestionSubmitStatusEnum.FAILED.getValue());
        updateSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitMapper.updateById(updateSubmit);
        if (update == 0) {
            throw new BusinessException("题目状态更新失败");
        }

        // 6、修改题目的通过数
        if(judgeResult){
            //将question的通过数+1
            questionMapper.update(null, new UpdateWrapper<Question>()
                    .setSql("accepted_num = accepted_num + 1").eq("id", question.getId()));
        }

        return updateSubmit;
    
    }
}
