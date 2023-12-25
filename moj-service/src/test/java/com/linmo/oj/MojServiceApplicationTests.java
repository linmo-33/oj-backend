package com.linmo.oj;

import com.linmo.oj.codesandbox.CodeSandbox;
import com.linmo.oj.codesandbox.CodeSandboxFactory;
import com.linmo.oj.codesandbox.model.ExecuteCodeRequest;
import com.linmo.oj.codesandbox.model.ExecuteCodeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MojServiceApplicationTests {

    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    @Test
    void executeCode() {
        CodeSandbox codeSandbox = codeSandboxFactory.newInstance("remote");
        String language = "java";
        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "      int a = Integer.parseInt(args[0]);\n" +
                "      int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果：\" + (a+b));\n" +
                "    }\n" +
                "}";
        List<String> inputList = Arrays.asList("1 2","2 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse response = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(response);
    }

}
