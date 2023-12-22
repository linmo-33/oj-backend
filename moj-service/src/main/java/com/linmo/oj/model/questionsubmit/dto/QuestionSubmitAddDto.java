package com.linmo.oj.model.questionsubmit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-21 16:56
 */
@Data
public class QuestionSubmitAddDto {

    /**
     * 编程语言
     */
    @ApiModelProperty(value = "编程语言")
    private String language;

    /**
     * 用户代码
     */
    @ApiModelProperty(value = "用户代码")
    private String code;

    /**
     * 题目 id
     */
    @ApiModelProperty(value = "题目 id")
    private Long questionId;


}
