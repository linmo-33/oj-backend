package com.linmo.oj.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author ljl
 * @since 2023-12-06 16:59
 */
@TableName(value ="user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 昵称
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "user_password")
    private String userPassword;

    /**
     * 账号状态（0正常 1停用）
     */
    @TableField(value = "user_status")
    private String userStatus;

    /**
     * 邮箱
     */
    @TableField(value = "user_email")
    private String userEmail;

    /**
     * 用户简介
     */
    @TableField(value = "user_profile")
    private String userProfile;

    /**
     * 手机号
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 用户性别（0男，1女，2未知）
     */
    @TableField(value = "user_sex")
    private String userSex;

    /**
     * 头像
     */
    @TableField(value = "user_avatar")
    private String userAvatar;

    /**
     * 用户类型（0管理员，1普通用户）
     */
    @TableField(value = "user_type")
    private String userType;

    /**
     * 创建人
     */
    @TableField(value = "create_name")
    private String createName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_name")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

}
