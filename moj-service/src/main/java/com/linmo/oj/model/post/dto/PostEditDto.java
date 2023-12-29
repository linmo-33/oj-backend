package com.linmo.oj.model.post.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-26 09:50
 */
@Data
public class PostEditDto {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "缩略图")
    @NotBlank(message = "缩略图不能为空")
    private String img;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "标签列表（json 数组）")
    private List<String> tags;

    @ApiModelProperty(value = "帖子状态(0正常 1关闭 2待审核)")
    private String status;
}
