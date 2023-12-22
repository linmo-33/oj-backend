package com.linmo.oj.model.sysresource.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-19 16:49
 */
@Data
public class SysRoleResourceDto {

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "资源id列表")
    private List<Long> ResourceIds;
}
