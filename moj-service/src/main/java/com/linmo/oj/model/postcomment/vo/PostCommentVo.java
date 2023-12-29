package com.linmo.oj.model.postcomment.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-26 15:08
 */
@Data
public class PostCommentVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "帖子id")
    private Long postId;

    @ApiModelProperty(value = "评论用户id")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "头像")
    private String userAvatar;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论时间")
    private Date createTime;
}
