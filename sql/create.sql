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
  AUTO_INCREMENT = 1
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
  AUTO_INCREMENT = 1
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
  AUTO_INCREMENT = 1
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
    `user_profile`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT '这个人很懒，什么都没写。' COMMENT '用户简介',
    `user_phone`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '手机号',
    `user_sex`      varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
    `user_avatar`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT 'http://minioview.ikuns.live/oj-b/user_avatar/default/default.jpg' COMMENT '头像',
    `user_type`     varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
    `create_name`   varchar(64)                                                   NULL     DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name`   varchar(64)                                                   NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`   datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`      int(0)                                                        NULL     DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `id`          bigint(0)   NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(0)   NOT NULL COMMENT '用户id',
    `role_id`     bigint(0)   NOT NULL DEFAULT 0 COMMENT '角色id',
    `create_name` varchar(64) NULL     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64) NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0) NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`    int(0)      NULL     DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
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
    `id`             bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `operation`      varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作',
    `url`            varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求地址',
    `ip`             varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'IP地址',
    `result`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '执行结果',
    `operator`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作人',
    `operation_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    `del_flag`       tinyint(0)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统日志'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `id`          bigint(0)                                                      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '标题',
    `content`     varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '内容',
    `status`      varchar(3)                                                     NOT NULL DEFAULT 0 COMMENT '状态(0未发布 1已发布 2已关闭)',
    `remark`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '备注',
    `create_name` varchar(64)                                                    NULL     DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0)                                                    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64)                                                    NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                                    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`    tinyint(0)                                                     NOT NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统公告'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`
(
    `id`           bigint(0)                                                      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '题目标题',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '题目内容',
    `difficulty`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT '0' COMMENT '题目难度(0简单，1中等，2困难)',
    `tags`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '题目标签列表（json 数组）',
    `answer`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL COMMENT '答案',
    `submit_num`   int(0)                                                         NOT NULL DEFAULT 0 COMMENT '题目提交数',
    `accepted_num` int(0)                                                         NOT NULL DEFAULT 0 COMMENT '题目通过数',
    `judge_case`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL COMMENT '判题用例（json数组）',
    `judge_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NOT NULL COMMENT '判题配置（json对象）',
    `create_name`  varchar(64)                                                    NULL     DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime(0)                                                    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name`  varchar(64)                                                    NULL     DEFAULT NULL COMMENT '更新人',
    `update_time`  datetime(0)                                                    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`     tinyint(0)                                                     NOT NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '题目'
  ROW_FORMAT = Dynamic;
INSERT INTO `question`
VALUES (1, 'A+B',
        '**描述**\n\n给出两个整数，请你输出他们的和。\n\n**输入描述**\n\n输两个整数 A, B。\n\n**输出描述**\n\n输出一个整数表示 A+B。\n\n**示例 1：**\n\n    输入：1 1\n  \n    输出：2\n    \n**示例 2：**\n\n    输入：1 5\n    \n    输出：6\n    \n**示例 3：**\n\n    输入： 9 6\n    \n    输出：15\n    \n**示例 4：**\n\n    输入：2 4\n    \n    输出：6\n',
        '0', '[\"数学\"]', '我也不知道', 10, 9, '[{\"input\":\"1 2\",\"output\":\"3 4\"}]',
        '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 2, ' ', '2023-10-12 21:42:32', ' ',
        '2023-10-15 16:46:19', 0);
INSERT INTO `question`
VALUES (2, '整数反转',
        '## 整数反转\n给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。\n\n如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。\n\n假设环境不允许存储 64 位整数（有符号或无符号）。\n\n**示例 1：**\n\n    输入：x = 123\n  \n    输出：321\n    \n**示例 2：**\n\n    输入：x = -123\n    \n    输出：-321\n    \n**示例 3：**\n\n    输入：x = 120\n    \n    输出：21\n    \n**示例 4：**\n\n    输入：x = 0\n    \n    输出：0',
        '1', '[\"数学\"]', 'unKnow', 0, 0, '[{\"input\":\"\",\"output\":\"\"}]',
        '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 2, ' ', '2023-10-12 21:42:32', ' ',
        '2023-10-15 16:46:19', 0);
INSERT INTO `question`
VALUES (3, 'N 皇后',
        '**N 皇后**\n\n按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。\n\nn 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。\n\n给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。\n\n每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 \'Q\' 和 \'.\' 分别代表了皇后和空位。\n\n**示例 1：**\n\n    输入：n = 1\n  \n    输出：[[\"Q\"]]\n    \n**示例 2：**\n\n    输入：n = 4\n    \n    输出：[[\".Q..\",\"...Q\",\"Q...\",\"..Q.\"],[\"..Q.\",\"Q...\",\"...Q\",\".Q..\"]]\n    \n    解释：如上所示，4 皇后问题存在两个不同的解法。\n    ',
        '2', '[\"数组\",\"回溯\"]', '', 0, 0,
        '[{\"input\":\"n \\u003d 1\",\"output\":\"[[\\\"Q\\\"]]\"},{\"input\":\"n \\u003d 4\",\"output\":\"[[\\\".Q..\\\",\\\"...Q\\\",\\\"Q...\\\",\\\"..Q.\\\"],[\\\"..Q.\\\",\\\"Q...\\\",\\\"...Q\\\",\\\".Q..\\\"]]\"}]',
        '{\"timeLimit\":1000,\"memoryLimit\":1000,\"stackLimit\":1000}', 0, 0, 2, ' ', '2023-10-12 21:42:32', ' ',
        '2023-10-15 16:46:19', 0);

-- ----------------------------
-- Table structure for question_submit
-- ----------------------------
DROP TABLE IF EXISTS `question_submit`;
CREATE TABLE `question_submit`
(
    `id`          bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `language`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编程语言',
    `code`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NOT NULL COMMENT '用户代码',
    `judge_Info`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci         NOT NULL COMMENT '判题信息（json对象）',
    `status`      int(0)                                                        NOT NULL DEFAULT 0 COMMENT '判题状态0-待判题、1-判题中、2-成功、3-失败',
    `question_id` bigint(0)                                                     NOT NULL COMMENT '题目 id',
    `user_id`     bigint(0)                                                     NOT NULL COMMENT '提交用户 id',
    `create_time` datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64)                                                   NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                                   NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`    tinyint(0)                                                    NOT NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '题目提交'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`
(
    `id`          bigint(0)                                                      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`       varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '标题',
    `img`         varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '缩略图',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci          NULL COMMENT '内容',
    `tags`        varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '标签列表（json 数组）',
    `comment_num`  int(0)                                                         NOT NULL DEFAULT 0 COMMENT '评论数',
    `status`      varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci    NULL     DEFAULT '2' COMMENT '帖子状态(0正常 1关闭 2待审核)',
    `user_id`     bigint(0)                                                      NOT NULL COMMENT '创建用户 id',
    `create_time` datetime(0)                                                    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_name` varchar(64)                                                    NULL     DEFAULT NULL COMMENT '更新人',
    `update_time` datetime(0)                                                    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
    `del_flag`    tinyint(0)                                                     NOT NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '帖子'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `post_comment`;
CREATE TABLE `post_comment`
(
    `id`          bigint     NOT NULL AUTO_INCREMENT,
    `post_id`     bigint     NOT NULL COMMENT '帖子id',
    `user_id`     bigint     NOT NULL COMMENT '评论用户id',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评论内容',
    `create_time` datetime                                              DEFAULT CURRENT_TIMESTAMP,
    `del_flag`    tinyint(0) NOT NULL                                   DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '帖子评论'
  ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;