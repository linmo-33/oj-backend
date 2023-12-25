package com.linmo.oj.template.java;

import cn.hutool.core.util.StrUtil;
import com.linmo.oj.model.dto.ExecuteResult;
import com.linmo.oj.utils.ProcessUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.linmo.oj.constants.SandBoxConstants.TIME_OUT;

/**
 * @author ljl
 * @description Args模式的Java沙箱
 * @date 2023-12-22 18:23
 */
@Service
public class JavaNativeArgsSandbox extends JavaSandboxTemplate {
    @Override
    protected List<ExecuteResult> runCode(String dir, List<String> inputList) throws IOException {
        List<ExecuteResult> executeResults = new ArrayList<>();
        for (String input : inputList) {
            //Linux下的命令
            //String runCmd = String.format("/software/jdk1.8.0_361/bin/java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", dir, input);
            //Windows下命令
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", dir, input);
            //监视器
            StopWatch stopWatch = new StopWatch();
            //开始计时
            stopWatch.start();
            //执行命令
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
            ExecuteResult executeResult = ProcessUtils.getProcessMessage(runProcess, "运行");
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