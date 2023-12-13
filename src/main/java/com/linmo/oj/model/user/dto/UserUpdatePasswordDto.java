package com.linmo.oj.model.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 13:48
 */
@Data
public class UserUpdatePasswordDto {

    @NotBlank(message = "用户账号不能为空")
    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
