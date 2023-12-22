package com.linmo.oj.template.java;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.WordTree;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.model.dto.ExecuteCodeResponse;
import com.linmo.oj.model.dto.ExecuteResult;
import com.linmo.oj.model.enums.ExecuteCodeStatusEnum;
import com.linmo.oj.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.linmo.oj.constants.SandBoxConstants.JAVA_CLASS_NAME;

/**
 * @author ljl
 * @description  Java沙箱模板
 * @date 2023-11-26 17:50
 */
@Slf4j
public abstract class JavaSandboxTemplate {
    private static final WordTree WORD_TREE;

    static {
        WORD_TREE = new WordTree();
        WORD_TREE.addWords("Files", "exec");
    }

    /**
     * 模板方法，定义运行步骤（Java的）
     */
    public final ExecuteCodeResponse executeJavaCode(List<String> inputList, String code) {
        //1. 把用户的代码保存为文件
        String dir = null;
        File codeFile = null;
        try {
            dir = System.getProperty("user.dir") + File.separator + "tmpCode" + File.separator + UUID.randomUUID();
            codeFile = saveFile(code, dir);
        } catch (BusinessException e) {
            return ExecuteCodeResponse.builder()
                    .code(ExecuteCodeStatusEnum.COMPILE_FAILED.getValue())
                    .msg(ResultCode.DANGER_CODE.getMessage())
                    .build();
        }

        //2. 编译代码，得到 class 文件
        try {
            ExecuteResult compileMessage = compile(codeFile);
            log.info("编译信息：{}", compileMessage);
            //编译已经失败了
            if(compileMessage.getExitValue() != 0){
                return ExecuteCodeResponse.builder()
                        .code(ExecuteCodeStatusEnum.COMPILE_FAILED.getValue())
                        .msg(compileMessage.getErrorOutput())
                        .build();
            }
        } catch (IOException e) {
            return ExecuteCodeResponse.builder()
                    .code(ExecuteCodeStatusEnum.COMPILE_FAILED.getValue())
                    .msg(e.toString())
                    .build();
        }

        //3.执行代码，获取结果列表，这一步有不同的实现
        List<ExecuteResult> executeResults;
        try {
            executeResults = runCode(dir, inputList);
            //证明有错误
            ExecuteResult lastResult = executeResults.get(executeResults.size() - 1);
            if(StrUtil.isNotEmpty(lastResult.getErrorOutput())){
                return ExecuteCodeResponse.builder()
                        .code(ExecuteCodeStatusEnum.RUN_FAILED.getValue())
                        .msg(lastResult.getErrorOutput())
                        .build();
            }
        } catch (IOException e) {
            return ExecuteCodeResponse.builder()
                    .code(ExecuteCodeStatusEnum.RUN_FAILED.getValue())
                    .msg(e.toString())
                    .build();
        }

        //4. 收集整理输出结果
        ExecuteCodeResponse response = arrangeResponse(executeResults);

        //5. 文件清理
        clearFile(codeFile, dir);
        return response;
    }

    /**
     * 将code保存在dir目录下
     * @param code 代码内容
     * @param dir 保存目录
     * @return 代码文件
     */
    protected File saveFile(String code, String dir) {
        //检查代码内容，是否有黑名单代码
        FoundWord foundWord = WORD_TREE.matchWord(code);
        if(foundWord != null){
            throw new BusinessException(ResultCode.DANGER_CODE);
        }

        String path = dir + File.separator + JAVA_CLASS_NAME;

        log.info("保存目录：{}", dir);
        return FileUtil.writeUtf8String(code, path);
    }

    /**
     * 对codeFile进行编译
     * @param codeFile 代码文件
     * @return 编译结果
     * @throws IOException 编译失败
     */
    protected ExecuteResult compile(File codeFile) throws IOException {
        // Linux下的命令
        String compileCmd = String.format("/software/jdk1.8.0_181/bin/javac -encoding utf-8 %s", codeFile.getAbsolutePath());
        // Windows下的命令
//         String compileCmd = String.format("javac -encoding utf-8 %s", codeFile.getAbsolutePath());

        //创建并执行process
        log.info("执行命令：{}", compileCmd);

        Process compileProcess = Runtime.getRuntime().exec(compileCmd);
        //拿到process执行信息
        return ProcessUtils.getProcessMessage(compileProcess, "编译");
    }

    /**
     * 运行代码，这一步针对Args和ACM有不同实现
     * @param dir 代码目录
     * @param inputList 输入列表
     * @return 运行结果列表
     */
    protected abstract List<ExecuteResult> runCode(String dir, List<String> inputList) throws IOException;

    /**
     * 收集整理运行结果
     * @param executeResults 运行结果列表
     * @return 整理后的结果
     */
    protected ExecuteCodeResponse arrangeResponse(List<ExecuteResult> executeResults){
        return ExecuteCodeResponse.builder()
                .code(ExecuteCodeStatusEnum.SUCCESS.getValue())
                .msg(ExecuteCodeStatusEnum.SUCCESS.getMsg())
                .results(executeResults).build();
    }

    /**
     * 文件清理
     * @param codeFile 代码文件
     * @param dir 代码目录
     */
    protected void clearFile(File codeFile, String dir){
        if (codeFile.getParentFile() != null) {
            boolean del = FileUtil.del(dir);
            log.info("删除{}: {}", del ? "成功" : "失败", dir);
        }
    }
}
