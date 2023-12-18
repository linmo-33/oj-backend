package com.linmo.oj.model.sysresource.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 16:42
 */
@Data
public class SysResourceAddDto {

    @ApiModelProperty(value = "资源名称")
    @NotBlank(message = "资源名称不能为空")
    private String resourceName;

    @ApiModelProperty(value = "资源Code")
    @NotBlank(message = "资源Code不能为空")
    private String resourceCode;

    @ApiModelProperty(value = "备注")
    private String remark;
}
