package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.sysrole.SysRole;
import com.linmo.oj.model.sysrole.dto.SysUserRoleDto;
import com.linmo.oj.model.user.dto.*;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-07 15:21
 */
@RestController
@Api(tags = "User")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册")
    @PostMapping (value = "/register")
    public BaseResponse<Long> register(@Validated @RequestBody UserRegisterDto userRegisterDto) {
        return ResultUtils.success(userService.register(userRegisterDto));
    }

    @ApiOperation(value = "用户登录")
    @PostMapping (value = "/login")
    //@LogRecord(value = "用户登录")
    public BaseResponse<String> login(@Validated @RequestBody UserLoginDto userLoginDto) {
        return ResultUtils.success(userService.login(userLoginDto.getUserAccount(),userLoginDto.getUserPassword()));
    }

    @ApiOperation(value = "用户注销登录")
    @PostMapping (value = "/logout")
    public BaseResponse<Boolean> logout() {
        return ResultUtils.success(userService.logout());
    }

    @ApiOperation(value = "获取登录用户")
    @PostMapping (value = "/getUser")
    public BaseResponse<UserVo> getLoginUser() {
        return ResultUtils.success(userService.getLoginUser());
    }

    @ApiOperation(value = "新增用户")
    @PostMapping (value = "/create")
    @LogRecord(value = "新增用户")
    @SaCheckPermission(value = "user.create", orRole = "admin")
    public BaseResponse<Boolean> createUser(@Validated @RequestBody UserAddDto userAddDto) {
        return ResultUtils.success(userService.create(userAddDto));
    }

    @ApiOperation(value = "修改用户")
    @PostMapping (value = "/update")
    @LogRecord(value = "修改用户")
    @SaCheckPermission(value = "user.update", orRole = "admin")
    public BaseResponse<Boolean> updateUser(@Validated @RequestBody UserEditDto userEditDto) {
        return ResultUtils.success(userService.update(userEditDto));
    }

    @ApiOperation(value = "删除用户")
    @PostMapping (value = "/delete")
    @LogRecord(value = "删除用户")
    @SaCheckPermission(value = "user.delete", orRole = "admin")
    public BaseResponse<Boolean> deleteUser(Long id) {
        return ResultUtils.success(userService.delete(id));}

    @ApiOperation(value = "根据id获取用户")
    @PostMapping (value = "/queryById")
    @SaCheckPermission(value = "user.queryById", orRole = "admin")
    public BaseResponse<UserVo> queryUserById(Long id) {
        return ResultUtils.success(userService.queryById(id));}

    @ApiOperation(value = "修改密码")
    @PostMapping (value = "/updatePassword")
    @LogRecord(value = "修改密码")
    public BaseResponse<Boolean> updatePassword(@Validated @RequestBody UserUpdatePasswordDto userUpdatePasswordDto) {
        return ResultUtils.success(userService.updatePassword(userUpdatePasswordDto));}

    @ApiOperation(value = "修改用户状态")
    @PostMapping (value = "/updateStatus")
    @LogRecord(value = "修改用户状态")
    @SaCheckPermission(value = "user.updateStatus", orRole = "admin")
    public BaseResponse<Boolean> banUser(Long id,String status) {
        return ResultUtils.success(userService.banUser(id,status));}

    @ApiOperation(value = "分页查询用户")
    @PostMapping (value = "/queryList")
    @SaCheckPermission(value = "user.queryList", orRole = "admin")
    public BaseResponse<PageResult<UserVo>> queryListUser(@Validated @RequestBody UserQueryDto userQueryDto) {
        return ResultUtils.success(userService.queryByPage(userQueryDto));}

    @ApiOperation(value = "上传头像")
    @PostMapping (value = "/uploadAvatar")
    @LogRecord(value = "上传头像")
    public BaseResponse<String> uploadAvatar(@RequestPart("file") MultipartFile multipartFile) {
        return ResultUtils.success(userService.uploadAvatar(multipartFile));}


    @ApiOperation(value = "给用户分配角色")
    @PostMapping (value = "/updateRole")
    @LogRecord(value = "修改用户状态")
    @SaCheckPermission(value = "user.updateRole", orRole = "admin")
    public BaseResponse<Boolean> updateRole(@RequestBody SysUserRoleDto sysUserRoleDto) {
        return ResultUtils.success(userService.updateRole(sysUserRoleDto));}

    @ApiOperation(value = "获取指定用户的角色")
    @PostMapping (value = "/getRole")
    @LogRecord(value = "获取指定用户的角色")
    @SaCheckPermission(value = "user.getRole", orRole = "admin")
    public BaseResponse<List<SysRole>> getRole(Long id) {
        return ResultUtils.success(userService.getUserRoleById(id));}


}
