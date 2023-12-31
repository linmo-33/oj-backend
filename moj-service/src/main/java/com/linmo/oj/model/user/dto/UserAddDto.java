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
public class UserAddDto {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String userAccount;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "邮箱")
    private String userEmail;

    @ApiModelProperty(value = "用户简介")
    private String userProfile;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "性别（0男 1女 2未知）")
    private String userSex;

    @ApiModelProperty(value = "头像")
    private String userAvatar;

    @ApiModelProperty(value = "用户类型（0普通用户 1管理员）")
    private String userType;

}
