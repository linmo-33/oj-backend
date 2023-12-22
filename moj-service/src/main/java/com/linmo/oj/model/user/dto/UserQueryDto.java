package com.linmo.oj.model.user.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询用户dto
 *
 * @author ljl
 * @since 2023-12-07 13:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDto extends Pager {

    @ApiModelProperty(value = "账号")
    private String userAccount;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "账号状态（0正常 1停用）")
    private String userStatus;

    @ApiModelProperty(value = "用户类型（0普通用户 1管理员）")
    private String userType;
}
