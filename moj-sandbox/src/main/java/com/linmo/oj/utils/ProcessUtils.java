package com.linmo.oj.utils;

import com.linmo.oj.model.dto.ExecuteResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程工具类
 */
@Slf4j
public class ProcessUtils {
    /**
     * 获取进程执行信息
     * @param process 进程
     * @return 执行结果
     */
    public static ExecuteResult getProcessMessage(Process process, String opName) {
        ExecuteResult executeResult = new ExecuteResult();

        try {
            //计时
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            // 等待程序执行，获取退出码
            int exitValue = process.waitFor();
            executeResult.setExitValue(exitValue);
            // 错误退出
            if(exitValue != 0){
                executeResult.setErrorOutput(getProcessOutput(process.getErrorStream()));
            } else {
                executeResult.setOutput(getProcessOutput(process.getInputStream()));
            }

            stopWatch.stop();
            executeResult.setTime(stopWatch.getLastTaskTimeMillis());
        } catch (Exception e) {
            log.error(opName + "失败：{}", e.toString());
        }
        return executeResult;
    }

    /**
     * 执行交互式进程并获取信息
     * @param runProcess 运行的进程
     * @param input 输入
     * @return  执行结果
     */
    public static ExecuteResult getAcmProcessMessage(Process runProcess, String input) throws IOException {
        ExecuteResult executeResult = new ExecuteResult();

        StringReader inputReader = new StringReader(input);
        BufferedReader inputBufferedReader = new BufferedReader(inputReader);

        //计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //输入（模拟控制台输入）
        PrintWriter consoleInput = new PrintWriter(runProcess.getOutputStream());
        String line;
        while ((line = inputBufferedReader.readLine()) != null) {
            consoleInput.println(line);
            consoleInput.flush();
        }
        consoleInput.close();

        //获取输出
        BufferedReader userCodeOutput = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
        List<String> outputList = new ArrayList<>();
        String outputLine;
        while ((outputLine = userCodeOutput.readLine()) != null) {
            // 使用 trim() 移除末尾的换行符
            outputLine = outputLine.trim();
            outputList.add(outputLine);
        }
        userCodeOutput.close();

        //获取错误输出
        BufferedReader errorOutput = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
        List<String> errorList = new ArrayList<>();
        String errorLine;
        while ((errorLine = errorOutput.readLine()) != null) {
            errorList.add(errorLine);
        }
        errorOutput.close();

        stopWatch.stop();
        executeResult.setTime(stopWatch.getLastTaskTimeMillis());
        executeResult.setOutput(StringUtils.join(outputList, "\n"));
        executeResult.setErrorOutput(StringUtils.join(errorList, "\n"));
        runProcess.destroy();

        return executeResult;
    }

    /**
     * 获取某个流的输出
     * @param inputStream  输入流
     * @return 输出
     * @throws IOException IO异常
     */
    public static String getProcessOutput(InputStream inputStream) throws IOException {
        // 分批获取进程的正常输出
        // Linux写法
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //Windows写法
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder outputSb = new StringBuilder();
        // 逐行读取
        String outputLine;
        while ((outputLine = bufferedReader.readLine()) != null) {
            outputSb.append(outputLine);
        }
        bufferedReader.close();
        return outputSb.toString();
    }
}
