package com.linmo.oj.model.sysrole.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 编辑角色Dto
 *
 * @author ljl
 * @since 2023-12-11 17:29
 */
@Data
public class SysRoleEditDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "角色权限名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "更新人")
    private String updateName;
}
