package com.linmo.oj.model.syslog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 * @TableName sys_log
 */
@TableName(value ="sys_log")
@Data
public class SysLog implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作
     */
    @TableField(value = "operation")
    private String operation;

    /**
     * 请求地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * IP地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 执行结果
     */
    @TableField(value = "result")
    private String result;

    /**
     * 操作人
     */
    @TableField(value = "operator")
    private String operator;

    /**
     * 操作时间
     */
    @TableField(value = "operation_time")
    private Date operationTime;

    /**
     * 是否删除
     */
    @TableField(value = "del_flag")
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}