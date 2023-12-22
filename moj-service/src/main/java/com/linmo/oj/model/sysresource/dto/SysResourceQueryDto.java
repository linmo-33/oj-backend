package com.linmo.oj.model.sysresource.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 16:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysResourceQueryDto extends Pager {

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源Code")
    private String resourceCode;
}
