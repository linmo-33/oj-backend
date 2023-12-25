package com.linmo.oj.codesandbox;

import com.linmo.oj.codesandbox.model.ExecuteCodeRequest;
import com.linmo.oj.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {

    /**
     *
     * @param executeCodeRequest 请求参数
     * @return 执行结果
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}