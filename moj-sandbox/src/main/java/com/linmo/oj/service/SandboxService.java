package com.linmo.oj.service;


import com.linmo.oj.model.dto.ExecuteCodeRequest;
import com.linmo.oj.model.dto.ExecuteCodeResponse;

/**
 * @author linmo
 * @description 代码沙箱接口定义
 * @date 2023-10-16 16:16
 */
public interface SandboxService {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return ExecuteCodeResponse
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
