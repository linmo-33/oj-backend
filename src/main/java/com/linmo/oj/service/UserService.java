package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.sysresource.SysResource;
import com.linmo.oj.model.sysrole.SysRole;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.dto.*;
import com.linmo.oj.model.user.vo.UserVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author ljl
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-12-06 17:23:07
*/
public interface UserService extends IService<User> {


    /**
     * 注册功能
     */
    Long register(UserRegisterDto userRegisterDto);

    /**
     * 登录功能
     * @param userAccount 用户名
     * @param userPassword 密码
     * @return 生成的JWT的token
     */
    String login(String userAccount,String userPassword);

    /**
     * 退出登录
     */
    Boolean logout();

    /**
     * 获取当前登录用户信息
     */
    UserVo getLoginUser();

    /**
     * 新增用户
     */
    Boolean create(UserAddDto addReq);

    /**
     * 修改用户信息
     */
    Boolean update(UserEditDto editReq);

    /**
     * 删除指定用户
     */
    Boolean delete(Long id);

    /**
     * 根据id查询用户信息
     */
    UserVo queryById(Long id);

    /**
     * 修改密码
     */
    Boolean updatePassword(UserUpdatePasswordDto userUpdatePasswordDto);

    /**
     * 禁用用户
     */
    Boolean banUser(Long id);

    /**
     * 解禁用户
     */
    Boolean unBanUser(Long id);

    /**
     * 分页查询用户列表
     */
    PageResult<UserVo> queryByPage(UserQueryDto queryReq);


    /**
     * 修改用户角色关系
     */
    @Transactional
    Boolean updateRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户对应角色
     */
    List<SysRole> getUserRoleById(Long userId);

    /**
     * 获取指定用户的可访问资源
     */
    List<SysResource> getResourceList(Long userId);
}
