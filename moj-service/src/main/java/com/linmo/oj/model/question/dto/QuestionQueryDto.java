package com.linmo.oj.model.question.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 20:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryDto extends Pager {

    @ApiModelProperty(value = "题目关键字")
    private String keyword;

    @ApiModelProperty(value = "题目难度(0简单，1中等，2困难)")
    private String difficulty;

    @ApiModelProperty(value = "题目状态（已通过、尝试过、未开始）")
    private String status;

    @ApiModelProperty(value = "题目标签列表（json 数组）")
    private List<String> tags;

}
