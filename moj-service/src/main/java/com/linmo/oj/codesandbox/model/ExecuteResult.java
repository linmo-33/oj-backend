package com.linmo.oj.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteResult {
    //退出码
    private Integer exitValue;
    //正常信息
    private String output;
    //错误信息
    private String errorOutput;
    //运行时间
    private Long time;
    //消耗内存
    private Long memory;
}