package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.sysrole.dto.SysRoleAddDto;
import com.linmo.oj.model.sysrole.dto.SysRoleEditDto;
import com.linmo.oj.model.sysrole.dto.SysRoleQueryDto;
import com.linmo.oj.model.sysrole.vo.SysRoleVo;
import com.linmo.oj.service.SysRoleService;
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
@Api(tags = "后台角色管理")
@RequestMapping("/sys_role")
public class SysRoleController {

    @Autowired
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
    @PostMapping(value = "/delete")
    @LogRecord(value = "删除角色")
    @SaCheckPermission(value = "sysRole.delete", orRole = "admin")
    public BaseResponse<Boolean> deleteSysRole(Long id) {
        return ResultUtils.success(sysRoleService.delete(id));
    }

    @ApiOperation(value = "根据id获取角色")
    @PostMapping(value = "/queryById")
    @SaCheckPermission(value = "sysRole.queryById", orRole = "admin")
    public BaseResponse<SysRoleVo> querySysRoleById(Long id) {
        return ResultUtils.success(sysRoleService.queryById(id));
    }

    @ApiOperation(value = "分页查询角色")
    @PostMapping(value = "/queryList")
    @SaCheckPermission(value = "sysRole.queryList", orRole = "admin")
    public BaseResponse<PageResult<SysRoleVo>> queryListSysRole(@Validated @RequestBody SysRoleQueryDto sysRoleQueryDto) {
        return ResultUtils.success(sysRoleService.queryByPage(sysRoleQueryDto));
    }


}
