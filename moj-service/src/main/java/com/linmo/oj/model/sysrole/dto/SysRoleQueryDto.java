package com.linmo.oj.model.sysrole.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-11 17:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleQueryDto extends Pager {

    @ApiModelProperty(value = "角色权限名称")
    private String roleName;

    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;
}
