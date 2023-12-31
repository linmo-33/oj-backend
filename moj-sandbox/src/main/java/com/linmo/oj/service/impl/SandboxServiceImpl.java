package com.linmo.oj.service.impl;

import com.linmo.oj.model.dto.ExecuteCodeRequest;
import com.linmo.oj.model.dto.ExecuteCodeResponse;
import com.linmo.oj.service.SandboxService;
import com.linmo.oj.template.cpp.CppSandboxTemplate;
import com.linmo.oj.template.java.JavaSandboxTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ljl
 * @description TODO
 * @date 2023-11-26 17:47
 */
@Service
public class SandboxServiceImpl implements SandboxService {

    @Resource
    private JavaSandboxTemplate javaNativeArgsSandbox;
    @Resource
    private CppSandboxTemplate cppNativeAcmSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        switch (language){
            case "java":
                return javaNativeArgsSandbox.executeJavaCode(inputList, code);
            case "cpp":
                return cppNativeAcmSandbox.executeCppCode(inputList, code);
            default:
                return null;
        }
    }
}
