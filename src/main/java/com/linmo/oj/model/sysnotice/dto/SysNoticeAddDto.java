package com.linmo.oj.model.sysnotice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 15:19
 */
@Data
public class SysNoticeAddDto {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "状态(0未发布 1已发布 2已关闭)")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
