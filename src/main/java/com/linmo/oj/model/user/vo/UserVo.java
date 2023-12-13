package com.linmo.oj.model.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 13:39
 */
@Data
public class UserVo {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "账号")
    private String userAccount;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "账号状态（0正常 1停用）")
    private String userStatus;

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

    @ApiModelProperty(value = "创建人")
    private Long createName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateName;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
