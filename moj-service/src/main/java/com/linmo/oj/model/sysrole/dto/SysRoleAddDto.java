package com.linmo.oj.model.sysrole.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增角色Dto
 *
 * @author ljl
 * @since 2023-12-11 17:27
 */
@Data
public class SysRoleAddDto {


    @ApiModelProperty(value = "角色权限名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;


    @ApiModelProperty(value = "备注")
    private String remark;
}
