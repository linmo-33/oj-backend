package com.linmo.oj.model.sysnotice.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 15:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysNoticeQueryDto extends Pager {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "状态(0未发布 1已发布 2已关闭)")
    private String status;
}
