package com.linmo.oj.model.question.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 20:46
 */
@Data
public class QuestionEditDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "题目标题")
    private String title;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "题目难度(0简单，1中等，2困难)")
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

    @ApiModelProperty(value = "点赞数")
    private Integer thumbUm;

    @ApiModelProperty(value = "收藏数")
    private Integer favourUm;


}
