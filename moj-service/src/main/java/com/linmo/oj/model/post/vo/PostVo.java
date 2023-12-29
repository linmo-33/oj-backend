package com.linmo.oj.model.post.vo;

import cn.hutool.json.JSONUtil;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.model.post.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-26 09:59
 */
@Data
public class PostVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "缩略图")
    private String img;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "标签列表（json 数组）")
    private List<String> tags;

    @ApiModelProperty(value = "评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "帖子状态(0正常 1关闭 2待审核)")
    private String status;

    @ApiModelProperty(value = "创建用户 id")
    private Long userId;

    @ApiModelProperty(value = "创建用户昵称")
    private String userName;

    @ApiModelProperty(value = "创建用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    /**
     * 对象转包装类
     *
     * @param post 对象
     * @return 包装类
     */
    public static PostVo objToVo(Post post) {
        if (post == null) {
            return null;
        }
        PostVo postVo = EntityConverter.copyAndGetSingle(post, PostVo.class);
        postVo.setTags(JSONUtil.toList(post.getTags(), String.class));
        return postVo;
    }
}
