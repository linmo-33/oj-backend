package com.linmo.oj.template.java;

import cn.hutool.core.util.StrUtil;
import com.linmo.oj.model.dto.ExecuteResult;
import com.linmo.oj.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.linmo.oj.constants.SandBoxConstants.TIME_OUT;

/**
 * @author ljl
 * @description ACM模式的Java沙箱
 * @date 2023-12-22 17:49
 */
@Service
@Slf4j
public class JavaNativeAcmSandbox extends JavaSandboxTemplate {
    @Value("${sandbox.security-manager-path:/www/wwwroot/oj-backend/security}")
    private String SECURITY_MANAGER_PATH;
    @Value("${sandbox.security-manager-class-name:MySecurityManager}")
    private String SECURITY_MANAGER_CLASS_NAME;

    @Override
    protected List<ExecuteResult> runCode(String dir, List<String> inputList) throws IOException {
        // 3. 执行代码，得到输出结果
        List<ExecuteResult> executeResults = new ArrayList<>();
        for (String input : inputList) {
            //Linux下的命令
            String runCmd = String.format("/software/jdk1.8.0_361/bin/java -Xmx256m -Dfile.encoding=UTF-8 -cp %s:%s -Djava.security.manager=%s Main", dir, SECURITY_MANAGER_PATH, SECURITY_MANAGER_CLASS_NAME);
            //Windows下的命令
//             String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s;%s -Djava.security.manager=%s Main", dir, SECURITY_MANAGER_PATH, SECURITY_MANAGER_CLASS_NAME);
            //String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main", dir);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            Process runProcess = Runtime.getRuntime().exec(runCmd);
            // 超时控制
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(TIME_OUT);
                    //超时了
                    runProcess.destroy();
                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
                }
            });
            thread.start();

            ExecuteResult executeResult = null;
            try {

                executeResult = ProcessUtils.getAcmProcessMessage(runProcess, input);
            } catch (IOException e){
                log.error("执行出错: {}", e.toString());
            }
            stopWatch.stop();
            if(!thread.isAlive()){
                executeResult = new ExecuteResult();
                executeResult.setTime(stopWatch.getLastTaskTimeMillis());
                executeResult.setErrorOutput("超出时间限制");
            }
            executeResults.add(executeResult);

            //已经有用例失败了
            if(StrUtil.isNotBlank(executeResult.getErrorOutput())){
                break;
            }
        }
        return executeResults;
    }
}
