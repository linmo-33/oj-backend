package com.linmo.oj.controller;

import com.linmo.oj.model.dto.ExecuteCodeRequest;
import com.linmo.oj.model.dto.ExecuteCodeResponse;
import com.linmo.oj.service.SandboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/sandbox")
@Slf4j
@Validated

public class SandboxController {

    // 认证请求头
    private static final String AUTH_REQUEST_HEADER = "auth";
    // 密钥 TODO: 加密传输
    private static final String AUTH_REQUEST_SECRET = "mHr9$JptTSlQtp%ACYpqTbgICrkR9tWiV24B0nRI0%p4Bvj8K$9hS9iaq!@@vWWtF1NneFqwNk%88EGRbO0W1yzaGJN#";

    @Resource
    private SandboxService sandboxService;

    @PostMapping("/execute")
    public ExecuteCodeResponse execute(@RequestBody ExecuteCodeRequest executeCodeRequest,
                                       HttpServletRequest request, HttpServletResponse response){
        // 简易认证
        String auth = request.getHeader(AUTH_REQUEST_HEADER);
        if(!AUTH_REQUEST_SECRET.equals(auth)){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        if (executeCodeRequest == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return sandboxService.executeCode(executeCodeRequest);
    }
}