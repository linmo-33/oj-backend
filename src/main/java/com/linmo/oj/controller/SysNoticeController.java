package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.sysnotice.dto.SysNoticeAddDto;
import com.linmo.oj.model.sysnotice.dto.SysNoticeEditDto;
import com.linmo.oj.model.sysnotice.dto.SysNoticeQueryDto;
import com.linmo.oj.model.sysnotice.vo.SysNoticeVo;
import com.linmo.oj.service.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 15:21
 */
@RestController
@Api(tags = "公告管理")
@RequestMapping("/sys_notice")
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;


    @ApiOperation(value = "新增公告")
    @PostMapping(value = "/create")
    @LogRecord(value = "新增公告")
    @SaCheckPermission(value = "sysNotice.create", orRole = "admin")
    public BaseResponse<Boolean> createSysNotice(@Validated @RequestBody SysNoticeAddDto sysNoticeAddDto) {
        return ResultUtils.success(sysNoticeService.create(sysNoticeAddDto));
    }

    @ApiOperation(value = "修改公告")
    @PostMapping(value = "/update")
    @LogRecord(value = "修改公告")
    @SaCheckPermission(value = "sysNotice.update", orRole = "admin")
    public BaseResponse<Boolean> updateSysNotice(@Validated @RequestBody SysNoticeEditDto sysNoticeEditDto) {
        return ResultUtils.success(sysNoticeService.update(sysNoticeEditDto));
    }

    @ApiOperation(value = "删除公告")
    @PostMapping(value = "/delete")
    @LogRecord(value = "删除公告")
    @SaCheckPermission(value = "sysNotice.delete", orRole = "admin")
    public BaseResponse<Boolean> deleteSysNotice(Long id) {
        return ResultUtils.success(sysNoticeService.delete(id));
    }

    @ApiOperation(value = "根据id获取公告")
    @PostMapping(value = "/queryById")
    @SaCheckPermission(value = "sysNotice.queryById", orRole = "admin")
    public BaseResponse<SysNoticeVo> querySysNoticeById(Long id) {
        return ResultUtils.success(sysNoticeService.queryById(id));
    }

    @ApiOperation(value = "分页查询公告")
    @PostMapping(value = "/queryList")
    @SaCheckPermission(value = "sysNotice.queryList", orRole = "admin")
    public BaseResponse<PageResult<SysNoticeVo>> queryListSysNotice(@Validated @RequestBody SysNoticeQueryDto sysNoticeQueryDto) {
        return ResultUtils.success(sysNoticeService.queryByPage(sysNoticeQueryDto));
    }


}
