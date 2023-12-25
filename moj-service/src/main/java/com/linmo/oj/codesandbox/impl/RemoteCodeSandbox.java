package com.linmo.oj.codesandbox.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.linmo.oj.codesandbox.CodeSandbox;
import com.linmo.oj.codesandbox.model.ExecuteCodeRequest;
import com.linmo.oj.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
@Service
@Slf4j
public class RemoteCodeSandbox implements CodeSandbox {

    // 认证请求头
    private static final String AUTH_REQUEST_HEADER = "auth";
    // 密钥
    private static final String AUTH_REQUEST_SECRET = "mHr9$JptTSlQtp%ACYpqTbgICrkR9tWiV24B0nRI0%p4Bvj8K$9hS9iaq!@@vWWtF1NneFqwNk%88EGRbO0W1yzaGJN#";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String requestBodyJson = JSONUtil.toJsonStr(executeCodeRequest);
        try (HttpResponse response = HttpRequest.post( "http://localhost:8090/api/sandbox/execute")
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(requestBodyJson)
                .execute()) {
            String responseBody = response.body();
            log.info("响应：{}", response);
            return JSONUtil.toBean(responseBody, ExecuteCodeResponse.class);
        } catch (Exception e) {
            log.info("请求沙箱失败：", e);
            return null;
        }
    }
}