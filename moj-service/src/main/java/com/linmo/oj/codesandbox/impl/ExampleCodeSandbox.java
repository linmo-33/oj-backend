package com.linmo.oj.codesandbox.impl;

import com.linmo.oj.codesandbox.CodeSandbox;
import com.linmo.oj.codesandbox.model.ExecuteCodeRequest;
import com.linmo.oj.codesandbox.model.ExecuteCodeResponse;
import com.linmo.oj.model.enums.ExecuteCodeStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
@Slf4j
@Service
public class ExampleCodeSandbox implements CodeSandbox {
    /**
     * 示例代码沙箱，假设执行总是成功
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setCode(ExecuteCodeStatusEnum.SUCCESS.getValue());
        response.setMsg(ExecuteCodeStatusEnum.SUCCESS.getText());
        return response;
    }
}