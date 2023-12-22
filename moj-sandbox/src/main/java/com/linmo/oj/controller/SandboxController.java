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

@RestController
@RequestMapping("/sandbox")
@Slf4j
@Validated
public class SandboxController {
    @Resource
    private SandboxService sandboxService;

    @PostMapping("/execute")
    public ExecuteCodeResponse execute(@RequestBody ExecuteCodeRequest executeCodeRequest){
        return sandboxService.executeCode(executeCodeRequest);
    }
}