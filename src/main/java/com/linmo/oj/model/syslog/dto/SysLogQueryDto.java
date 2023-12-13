package com.linmo.oj.model.syslog.dto;

import com.linmo.oj.common.api.Pager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 15:01
 */
@Data
public class SysLogQueryDto extends Pager {

    @ApiModelProperty(value = "操作模块")
    private String operation;

    @ApiModelProperty(value = "操作人")
    private String operator;
}
