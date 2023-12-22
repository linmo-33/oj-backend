package com.linmo.oj.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.common.utils.MinioUtils;
import com.linmo.oj.mapper.SysResourceMapper;
import com.linmo.oj.mapper.SysRoleMapper;
import com.linmo.oj.mapper.UserMapper;
import com.linmo.oj.model.sysresource.SysResource;
import com.linmo.oj.model.sysrole.SysRole;
import com.linmo.oj.model.sysrole.SysUserRole;
import com.linmo.oj.model.sysrole.dto.SysUserRoleDto;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.dto.*;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.SysUserRoleService;
import com.linmo.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ljl
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2023-12-06 17:23:07
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private SysResourceMapper resourceMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private MinioUtils minioUtils;


    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "Ib3DQEBAQUAA4GNADCBiQKBgQDvsJrdNacJvZRzHauwPIJBIoJNFyjH47/uCIwBYVgkok3j+3bZZ3HhSW5F";

    /**
     * 注册功能
     *
     * @param userRegisterDto 用户信息
     * @return 是否注册成功
     */
    @Override
    public Long register(UserRegisterDto userRegisterDto) {
        //校验两次密码是否一致
        if (!userRegisterDto.getUserPassword().equals(userRegisterDto.getCheckPassword())) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "两次密码输入不一致");
        }
        //校验用户名是否重复
        if (userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userRegisterDto.getUserAccount())) > 0) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "用户名已存在");
        }
        User user = EntityConverter.copyAndGetSingle(userRegisterDto, User.class);
        //密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userRegisterDto.getUserPassword()).getBytes());
        user.setUserPassword(encryptPassword);
        //生成随机用户昵称
        user.setUserName("用户" + System.currentTimeMillis());
        int result = userMapper.insert(user);
        if (result < 0) {
            throw new BusinessException(ResultCode.SYSTEM_ERROR, "注册失败");
        }
        return user.getId();
    }

    /**
     * 登录功能
     *
     * @param userAccount  用户名
     * @param userPassword 密码
     * @return 生成的JWT的token
     */
    @Override
    public String login(String userAccount, String userPassword) {
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //校验用户名和密码是否正确
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptPassword));
        if (BeanUtil.isEmpty(user)) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "用户名或密码错误");
        }
        //校验用户状态
        if ("1".equals(user.getUserStatus()) && StpUtil.isDisable(user.getId())) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "该用户已被禁用");
        }
        //生成token
        StpUtil.login(user.getId());
        //用户信息存入Redis
        UserVo userVo = EntityConverter.copyAndGetSingle(user, UserVo.class);
        StpUtil.getSession().set("loginUser", userVo);
        //获取用户资源权限Code集合
        List<SysResource> resourceList = resourceMapper.getResourceList(user.getId());
        List<String> codeList = resourceList.stream()
                .map(SysResource::getResourceCode)
                .collect(Collectors.toList());
        //将用户资源权限Code集合存入Redis
        StpUtil.getSession().set("resourceCodeList", codeList);
        //获取用户角色集合
        List<SysRole> userRoleList = roleMapper.getRoleList(user.getId());
        List<String> roleList = userRoleList.stream()
                .map(SysRole::getRoleKey)
                .collect(Collectors.toList());
        //将用户角色集合存入Redis
        StpUtil.getSession().set("roleList", roleList);
        return StpUtil.getTokenInfo().getTokenValue();
    }

    /**
     * 退出登录
     *
     * @return 是否退出成功
     */
    @Override
    public Boolean logout() {
        StpUtil.logout();
        //StpUtil.logoutByTokenValue(StpUtil.getTokenValue());
        return true;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息
     */
    @Override
    public UserVo getLoginUser() {
        //判断是否登录
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ResultCode.NOT_LOGIN_ERROR, "未登录");
        }
        UserVo userVo = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        if (BeanUtil.isNotEmpty(userVo)) {
            List<String> roleList = (List<String>) StpUtil.getSession().get("roleList");
            String userRole = String.join(",", roleList);
            userVo.setUserRole(userRole);
            return userVo;
        }
        return null;
    }

    /**
     * 新增用户
     *
     * @param addReq 用户信息
     * @return 是否新增成功
     */
    @Override
    public Boolean create(UserAddDto addReq) {
        //判断用户账号是否存在
        if (userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, addReq.getUserAccount())) > 0) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "用户名已存在");
        }
        User user = EntityConverter.copyAndGetSingle(addReq, User.class);
        // 设置默认用户名
        user.setUserName("用户" + System.currentTimeMillis());
        //设置默认密码 12345678
        user.setUserPassword("dfcc423069838f541756ca8ccc0fe8f7");
        //添加创建人
        user.setCreateName(getLoginUser().getUserAccount());
        return userMapper.insert(user) > 0;
    }

    /**
     * 修改用户信息
     *
     * @param editReq 用户信息
     * @return 是否修改成功
     */
    @Override
    public Boolean update(UserEditDto editReq) {
        //校验用户名是否重复
        if (userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, editReq.getUserAccount())
                .ne(User::getId, editReq.getId())) > 0) {
            throw new BusinessException("用户名已存在");
        }
        //校验用户是否存在
        if (BeanUtil.isEmpty(userMapper.selectById(editReq.getId()))) {
            throw new BusinessException("该用户不存在");
        }
        User user = EntityConverter.copyAndGetSingle(editReq, User.class);
        //添加更新人
        user.setUpdateName(getLoginUser().getUserAccount());
        return userMapper.updateById(user) > 0;
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    @Override
    public Boolean delete(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            User user = userMapper.selectById(id);
            if (BeanUtil.isEmpty(user)) {
                throw new BusinessException("该用户不存在");
            }
            return userMapper.deleteById(id) > 0;
        }
        return true;
    }

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    @Override
    public UserVo queryById(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getId, id));
            if (BeanUtil.isEmpty(user)) {
                throw new BusinessException("该用户不存在");
            }
            return EntityConverter.copyAndGetSingle(user, UserVo.class);
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @param userUpdatePasswordDto 用户信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updatePassword(UserUpdatePasswordDto userUpdatePasswordDto) {
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userUpdatePasswordDto.getOldPassword()).getBytes());
        //校验用户名和密码是否正确
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userUpdatePasswordDto.getUserAccount())
                .eq(User::getUserPassword, encryptPassword));
        if (BeanUtil.isEmpty(user)) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "原密码错误");
        }
        //修改密码
        user.setUserPassword(DigestUtils.md5DigestAsHex((SALT + userUpdatePasswordDto.getNewPassword()).getBytes()));

        return userMapper.update(user, new LambdaQueryWrapper<User>()
                .eq(User::getUserAccount, userUpdatePasswordDto.getUserAccount())) > 0;
    }

    /**
     * 禁用用户
     *
     * @param id 用户id
     * @return 是否禁用成功
     */
    @Override
    public Boolean banUser(Long id, String status) {
        if (BeanUtil.isNotEmpty(id)) {
            User user = userMapper.selectById(id);
            if (BeanUtil.isEmpty(user)) {
                throw new BusinessException("该用户不存在");
            }
            if ("1".equals(status)) {
                // 先踢下线
                StpUtil.kickout(id);
                // 再封禁账号
                StpUtil.disable(id, -1);
                //禁用用户
                user.setUserStatus("1");
            } else {
                //解禁用户
                StpUtil.untieDisable(id);
                user.setUserStatus("0");
            }

            return userMapper.updateById(user) > 0;
        }
        return null;
    }


    /**
     * 分页查询用户列表
     *
     * @param queryReq 查询条件
     * @return 用户列表
     */
    @Override
    public PageResult<UserVo> queryByPage(UserQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());

        //分页条件查询用户信息
        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<User>()
                .like(StrUtil.isNotBlank(queryReq.getUserAccount()), User::getUserAccount, queryReq.getUserAccount())
                .like(StrUtil.isNotBlank(queryReq.getUserName()), User::getUserName, queryReq.getUserName())
                .eq(StrUtil.isNotBlank(queryReq.getUserStatus()), User::getUserStatus, queryReq.getUserStatus())
                .eq(StrUtil.isNotBlank(queryReq.getUserType()), User::getUserType, queryReq.getUserType()));
        List<UserVo> pageList = EntityConverter.copyAndGetList(userList, UserVo.class);
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    /**
     * 修改用户角色关系
     *
     * @param sysUserRoleDto 用户角色信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateRole(SysUserRoleDto sysUserRoleDto) {
        //先删除原来的关系
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUserRole::getUserId, sysUserRoleDto.getUserId());
        sysUserRoleService.remove(wrapper);
        //建立新关系

        SysUserRole roleRelation = new SysUserRole();
        roleRelation.setUserId(sysUserRoleDto.getUserId());
        roleRelation.setRoleId(sysUserRoleDto.getRoleId());

        return sysUserRoleService.save(roleRelation);
    }


    /**
     * 获取用户对应角色
     *
     * @param userId 用户id
     * @return 角色列表
     */
    @Override
    public List<SysRole> getUserRoleById(Long userId) {
        return roleMapper.getRoleList(userId);
    }

    /**
     * 获取指定用户的可访问资源
     *
     * @param userId 用户id
     * @return 资源列表
     */
    @Override
    public List<SysResource> getResourceList(Long userId) {
        return resourceMapper.getResourceList(userId);
    }


    /**
     * 上传头像
     *
     * @param file 头像文件
     * @return 头像地址
     */
    @Override
    public String uploadAvatar(MultipartFile file) {
        // 文件目录：根据业务、用户来划分
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + file.getOriginalFilename();
        String filepath = String.format("%s/%s/%s", "user_avatar", getLoginUser().getId(), filename);
        try {
            // 返回可访问地址
            return minioUtils.uploadFile(file, filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ResultCode.SYSTEM_ERROR, "上传失败");
        }
    }
}




