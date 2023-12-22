package com.linmo.oj.model.questionsubmit.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-21 16:59
 */
@Data
public class SubmitSummaryVo {

    @ApiModelProperty(value = "题库总数")
    private Integer total;

    @ApiModelProperty(value = "简单通过数")
    private Integer easyPass;

    @ApiModelProperty(value = "简单总数")
    private Integer easyTotal;

    @ApiModelProperty(value = "中等通过数")
    private Integer mediumPass;

    @ApiModelProperty(value = "中等总数")
    private Integer mediumTotal;

    @ApiModelProperty(value = "困难通过数")
    private Integer hardPass;

    @ApiModelProperty(value = "困难总数")
    private Integer hardTotal;

    @ApiModelProperty(value = "提交总数")
    private Long submitCount;

    @ApiModelProperty(value = "通过总数")
    private Long passCount;
}
