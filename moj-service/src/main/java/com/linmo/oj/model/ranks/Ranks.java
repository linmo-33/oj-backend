package com.linmo.oj.model.ranks;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName ranks
 */
@TableName(value ="ranks")
@Data
public class Ranks implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;


    /**
     * 简单题目通过数
     */
    @TableField(value = "easy_pass")
    private Integer easyPass;


    /**
     * 中等题目通过数
     */
    @TableField(value = "medium_pass")
    private Integer mediumPass;


    /**
     * 困难题目通过数
     */
    @TableField(value = "hard_pass")
    private Integer hardPass;


    /**
     * 总提交数
     */
    @TableField(value = "submit_count")
    private Integer submitCount;

    /**
     * 总通过数
     */
    @TableField(value = "pass_count")
    private Integer passCount;

    /**
     * 得分
     */
    @TableField(value = "score")
    private Integer score;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}