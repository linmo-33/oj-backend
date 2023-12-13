package com.linmo.oj.model.syslog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 15:02
 */
@Data
public class SysLogVo {

    private Long id;

    @ApiModelProperty(value = "操作模块")
    private String operation;

    @ApiModelProperty(value = "请求地址")
    private String url;

    @ApiModelProperty(value = "IP地址")
    private String ip;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作时间")
    private Date operationTime;
}
