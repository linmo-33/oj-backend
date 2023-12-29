package com.linmo.oj.model.post.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-26 09:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryDto extends Pager {


    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态")
    private String status;

}
