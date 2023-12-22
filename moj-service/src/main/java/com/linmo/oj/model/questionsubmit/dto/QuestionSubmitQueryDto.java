package com.linmo.oj.model.questionsubmit.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-21 16:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionSubmitQueryDto extends Pager {

    /**
     * 题目 id
     */
    @ApiModelProperty(value = "题目 id")
    private Long questionId;
}
