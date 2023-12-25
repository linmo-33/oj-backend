package com.linmo.oj.model.questionsubmit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuestionRunResult {
    /**
     * 执行状态
     */
    @ApiModelProperty(value = "执行状态")
    private Integer code;
    /**
     * 输入
     */
    @ApiModelProperty(value = "输入")
    private String input;
    /**
     * 执行结果
     */
    @ApiModelProperty(value = "执行结果")
    private String output;

}