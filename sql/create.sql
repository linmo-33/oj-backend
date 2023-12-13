/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : demo-security

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 06/12/2023 17:07:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT,
    `resource_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'NULL' COMMENT '资源名称',
    `resource_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '资源Code',
    `remark`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '备注',
    `create_name`   varchar(64)                                                   NULL     DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name`   varchar(64)                                                   NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`      int(0)                                                        NULL     DEFAULT 0 COMMENT '是否删除（0未删除 1已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '后台资源表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(0)                                                     NOT NULL AUTO_INCREMENT,
    `role_name`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色权限名称',
    `role_key`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci      NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
    `create_name` varchar(64)                                                   NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64)                                                   NULL DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                                   NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`    int(0)                                                        NULL DEFAULT 0 COMMENT 'del_flag',

    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`
(
    `role_id`     bigint(0)   NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `resource_id` bigint(0)   NOT NULL DEFAULT 0 COMMENT '菜单id',
    `create_name` varchar(64) NULL     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64) NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`    int(0)      NULL     DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`role_id`, `resource_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_account`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'NULL' COMMENT '用户名',
    `user_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'NULL' COMMENT '昵称',
    `user_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT 'NULL' COMMENT '密码',
    `user_status`   varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    `user_email`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '邮箱',
    `user_profile`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '用户简介',
    `user_phone`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '手机号',
    `user_sex`      varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
    `user_avatar`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '头像',
    `user_type`     varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
    `create_name`   varchar(64)                                                   NULL     DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name`   varchar(64)                                                   NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`      int(0)                                                        NULL     DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id`     bigint(0)   NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `role_id`     bigint(0)   NOT NULL DEFAULT 0 COMMENT '角色id',
    `create_name` varchar(64) NULL     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64) NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`    int(0)      NULL     DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `operation`     varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作',
    `url`           varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求地址',
    `ip`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP地址',
    `operator`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作人',
    `operation_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    `del_flag`      tinyint(0)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `id`         bigint(0)                                                      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '标题',
    `content`    varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '内容',
    `status`     varchar(3)                                                     NOT NULL DEFAULT 0 COMMENT '状态(0未发布 1已发布 2已关闭)',
    `remark`     varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '备注',
    `create_name` varchar(64) NULL     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64) NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`   tinyint(0)                                                     NOT NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1726440778310557699
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统公告'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;