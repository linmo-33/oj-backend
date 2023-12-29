package com.linmo.oj.model.postcomment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-26 15:06
 */
@Data
public class PostCommentAddDto {

    @ApiModelProperty(value = "帖子id")
    @NotNull(message = "帖子id不能为空")
    private Long postId;

    @ApiModelProperty(value = "评论用户id")
    @NotNull(message = "评论用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

}
