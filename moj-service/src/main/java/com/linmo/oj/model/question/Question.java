package com.linmo.oj.model.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 题目标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 题目内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 题目难度(0简单，1中等，2困难)
     */
    @TableField(value = "difficulty")
    private String difficulty;

    /**
     * 题目标签列表（json 数组）
     */
    @TableField(value = "tags")
    private String tags;

    /**
     * 答案
     */
    @TableField(value = "answer")
    private String answer;

    /**
     * 题目提交数
     */
    @TableField(value = "submit_num")
    private Integer submitNum;

    /**
     * 题目通过数
     */
    @TableField(value = "accepted_num")
    private Integer acceptedNum;

    /**
     * 判题用例（json数组）
     */
    @TableField(value = "judge_case")
    private String judgeCase;

    /**
     * 判题配置（json对象）
     */
    @TableField(value = "judge_config")
    private String judgeConfig;


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


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}