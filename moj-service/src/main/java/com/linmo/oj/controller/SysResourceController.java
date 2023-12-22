package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.sysresource.SysResource;
import com.linmo.oj.model.sysresource.dto.SysResourceAddDto;
import com.linmo.oj.model.sysresource.dto.SysResourceEditDto;
import com.linmo.oj.model.sysresource.dto.SysResourceQueryDto;
import com.linmo.oj.model.sysresource.vo.SysResourceVo;
import com.linmo.oj.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 15:21
 */
@RestController
@Api(tags = "系统资源接口")
@RequestMapping("/sys_resource")
public class SysResourceController {

    @Resource
    private SysResourceService sysResourceService;


    @ApiOperation(value = "新增资源")
    @PostMapping(value = "/create")
    @LogRecord(value = "新增资源")
    @SaCheckPermission(value = "sysResource.create", orRole = "admin")
    public BaseResponse<Boolean> createSysResource(@Validated @RequestBody SysResourceAddDto sysResourceAddDto) {
        return ResultUtils.success(sysResourceService.create(sysResourceAddDto));
    }

    @ApiOperation(value = "修改资源")
    @PostMapping(value = "/update")
    @LogRecord(value = "修改资源")
    @SaCheckPermission(value = "sysResource.update", orRole = "admin")
    public BaseResponse<Boolean> updateSysResource(@Validated @RequestBody SysResourceEditDto sysResourceEditDto) {
        return ResultUtils.success(sysResourceService.update(sysResourceEditDto));
    }

    @ApiOperation(value = "删除资源")
    @PostMapping(value = "/delete")
    @LogRecord(value = "删除资源")
    @SaCheckPermission(value = "sysResource.delete", orRole = "admin")
    public BaseResponse<Boolean> deleteSysResource(Long id) {
        return ResultUtils.success(sysResourceService.delete(id));
    }

    @ApiOperation(value = "分页查询资源")
    @PostMapping(value = "/queryList")
    @SaCheckPermission(value = "sysResource.queryList", orRole = "admin")
    public BaseResponse<PageResult<SysResourceVo>> queryListSysResource(@Validated @RequestBody SysResourceQueryDto sysResourceQueryDto) {
        return ResultUtils.success(sysResourceService.queryByPage(sysResourceQueryDto));
    }

    @ApiOperation("查询所有资源")
    @GetMapping(value = "/listAll")
    @SaCheckPermission(value = "sysResource.list", orRole = "admin")
    public BaseResponse<List<SysResource>> listAll() {
        List<SysResource> resourceList = sysResourceService.list();
        return ResultUtils.success(resourceList);
    }


}
