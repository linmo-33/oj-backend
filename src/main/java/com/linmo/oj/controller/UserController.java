package com.linmo.oj.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.user.dto.*;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Api(tags = "后台用户管理")
@Tag(name = "UserController",description = "后台用户管理")
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
    @LogRecord(value = "用户登录")
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

    @ApiOperation(value = "禁用用户")
    @PostMapping (value = "/banUser")
    @LogRecord(value = "禁用用户")
    @SaCheckPermission(value = "user.banUser", orRole = "admin")
    public BaseResponse<Boolean> banUser(Long id) {
        return ResultUtils.success(userService.banUser(id));}

    @ApiOperation(value = "解禁用户")
    @PostMapping (value = "/unBanUser")
    @LogRecord(value = "解禁用户")
    @SaCheckPermission(value = "user.unBanUser", orRole = "admin")
    public BaseResponse<Boolean> unBanUser(Long id) {
        return ResultUtils.success(userService.unBanUser(id));}

    @ApiOperation(value = "分页查询用户")
    @PostMapping (value = "/queryList")
    @SaCheckPermission(value = "user.queryList", orRole = "admin")
    public BaseResponse<PageResult<UserVo>> queryListUser(@Validated @RequestBody UserQueryDto userQueryDto) {
        return ResultUtils.success(userService.queryByPage(userQueryDto));}



}
