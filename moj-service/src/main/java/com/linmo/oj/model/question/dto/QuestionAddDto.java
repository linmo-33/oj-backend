package com.linmo.oj.model.question.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 20:42
 */
@Data
public class QuestionAddDto {

    @ApiModelProperty(value = "题目标题")
    @NotBlank(message = "题目标题不能为空")
    private String title;

    @ApiModelProperty(value = "题目内容")
    @NotBlank(message = "题目内容不能为空")
    private String content;

    @ApiModelProperty(value = "题目难度(0简单，1中等，2困难)")
    @NotBlank(message = "题目难度不能为空")
    private String difficulty;

    @ApiModelProperty(value = "题目标签列表（json 数组）")
    private List<String> tags;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "题目提交数")
    private Integer submitNum;

    @ApiModelProperty(value = "题目通过数")
    private Integer acceptedNum;

    @ApiModelProperty(value = "判题用例（json数组）")
    private List<JudgeCase> judgeCase;

    @ApiModelProperty(value = "判题配置（json对象）")
    private JudgeConfig judgeConfig;


}
