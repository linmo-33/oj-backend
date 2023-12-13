package com.linmo.oj.model.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 13:40
 */
@Data
public class UserLoginDto {

    @NotBlank(message = "用户账号不能为空")
    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String userPassword;
}
