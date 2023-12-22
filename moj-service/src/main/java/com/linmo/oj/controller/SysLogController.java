package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.syslog.dto.SysLogQueryDto;
import com.linmo.oj.model.syslog.vo.SysLogVo;
import com.linmo.oj.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-13 15:32
 */
@RestController
@Api(tags = "系统日志接口")
@RequestMapping("/sys_log")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;

    @ApiOperation(value = "分页查询日志")
    @PostMapping(value = "/queryList")
    @SaCheckPermission(value = "sysLog.queryList", orRole = "admin")
    public BaseResponse<PageResult<SysLogVo>> queryListSysLog(@Validated @RequestBody SysLogQueryDto sysLogQueryDto) {
        return ResultUtils.success(sysLogService.queryByPage(sysLogQueryDto));
    }
}
