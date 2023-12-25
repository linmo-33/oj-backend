package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.model.sysresource.dto.SysRoleResourceDto;
import com.linmo.oj.model.sysrole.dto.SysRoleAddDto;
import com.linmo.oj.model.sysrole.dto.SysRoleEditDto;
import com.linmo.oj.model.sysrole.dto.SysRoleQueryDto;
import com.linmo.oj.model.sysrole.vo.SysRoleVo;
import com.linmo.oj.service.SysRoleService;
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
@Api(tags = "系统角色接口")
@RequestMapping("/sys_role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;


    @ApiOperation(value = "新增角色")
    @PostMapping(value = "/create")
    @LogRecord(value = "新增角色")
    @SaCheckPermission(value = "sysRole.create", orRole = "admin")
    public BaseResponse<Boolean> createSysRole(@Validated @RequestBody SysRoleAddDto sysRoleAddDto) {
        return ResultUtils.success(sysRoleService.create(sysRoleAddDto));
    }

    @ApiOperation(value = "修改角色")
    @PostMapping(value = "/update")
    @LogRecord(value = "修改角色")
    @SaCheckPermission(value = "sysRole.update", orRole = "admin")
    public BaseResponse<Boolean> updateSysRole(@Validated @RequestBody SysRoleEditDto sysRoleEditDto) {
        return ResultUtils.success(sysRoleService.update(sysRoleEditDto));
    }

    @ApiOperation(value = "删除角色")
    @GetMapping(value = "/delete")
    @LogRecord(value = "删除角色")
    @SaCheckPermission(value = "sysRole.delete", orRole = "admin")
    public BaseResponse<Boolean> deleteSysRole(@RequestParam("id") Long id) {
        return ResultUtils.success(sysRoleService.delete(id));
    }

    @ApiOperation(value = "根据id获取角色")
    @GetMapping(value = "/queryById")
    @SaCheckPermission(value = "sysRole.queryById", orRole = "admin")
    public BaseResponse<SysRoleVo> querySysRoleById(@RequestParam("id") Long id) {
        return ResultUtils.success(sysRoleService.queryById(id));
    }

    @ApiOperation(value = "分页查询角色")
    @PostMapping(value = "/queryList")
    @SaCheckPermission(value = "sysRole.queryList", orRole = "admin")
    public BaseResponse<PageResult<SysRoleVo>> queryListSysRole(@Validated @RequestBody SysRoleQueryDto sysRoleQueryDto) {
        return ResultUtils.success(sysRoleService.queryByPage(sysRoleQueryDto));
    }

    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    @SaCheckPermission(value = "sysRole.listAll", orRole = "admin")
    public BaseResponse<List<SysRoleVo>> listAll() {
        return ResultUtils.success(EntityConverter.copyAndGetList(sysRoleService.list(), SysRoleVo.class));
    }


    @ApiOperation(value = "给角色分配资源")
    @PostMapping(value = "/updateRoleResource")
    @LogRecord(value = "给角色分配资源")
    @SaCheckPermission(value = "sysRole.updateResource", orRole = "admin")
    public BaseResponse<Boolean> updateRoleResource(@RequestBody SysRoleResourceDto sysRoleResourceDto) {
        return ResultUtils.success(sysRoleService.updateResource(sysRoleResourceDto));
    }
}
