package com.linmo.oj.codesandbox.impl;

import com.linmo.oj.codesandbox.CodeSandbox;
import com.linmo.oj.codesandbox.model.ExecuteCodeRequest;
import com.linmo.oj.codesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Service;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
@Service
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}