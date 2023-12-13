package com.linmo.oj.model.sysresource.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 16:42
 */
@Data
public class SysResourceVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @ApiModelProperty(value = "资源Code")
    private String resourceCode;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新人")
    private String updateName;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

}
