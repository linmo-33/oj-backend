package com.linmo.oj.model.questionsubmit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QuestionRunRequest {

    @NotBlank
    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "输入")
    private String input;

    @NotBlank
    @ApiModelProperty(value = "语言")
    private String language;
}