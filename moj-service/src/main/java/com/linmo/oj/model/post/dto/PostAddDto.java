package com.linmo.oj.model.post.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-26 09:50
 */
@Data
public class PostAddDto {

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @TableField(value = "img")
    @NotBlank(message = "缩略图不能为空")
    private String img;

    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "标签列表（json 数组）")
    private List<String> tags;

}
