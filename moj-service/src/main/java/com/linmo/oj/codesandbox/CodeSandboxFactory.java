package com.linmo.oj.codesandbox;

import com.linmo.oj.codesandbox.impl.ExampleCodeSandbox;
import com.linmo.oj.codesandbox.impl.RemoteCodeSandbox;
import com.linmo.oj.codesandbox.impl.ThirdPartyCodeSandbox;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CodeSandboxFactory {
    @Resource
    private RemoteCodeSandbox remoteCodeSandbox;
    @Resource
    private ThirdPartyCodeSandbox thirdPartyCodeSandbox;
    @Resource
    private ExampleCodeSandbox exampleCodeSandbox;

    /**
     * 创建代码沙箱示例
     *
     * @param type 沙箱类型
     * @return
     */
    public CodeSandbox newInstance(String type) {
        switch (type) {
            case "remote":
                return remoteCodeSandbox;
            case "thirdParty":
                return thirdPartyCodeSandbox;
            case "example":
            default:
                return exampleCodeSandbox;
        }
    }
}