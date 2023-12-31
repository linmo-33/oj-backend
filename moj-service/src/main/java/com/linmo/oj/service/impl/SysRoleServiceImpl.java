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
import com.linmo.oj.mapper.SysRoleMapper;
import com.linmo.oj.model.sysresource.SysRoleResource;
import com.linmo.oj.model.sysresource.dto.SysRoleResourceDto;
import com.linmo.oj.model.sysrole.SysRole;
import com.linmo.oj.model.sysrole.dto.SysRoleAddDto;
import com.linmo.oj.model.sysrole.dto.SysRoleEditDto;
import com.linmo.oj.model.sysrole.dto.SysRoleQueryDto;
import com.linmo.oj.model.sysrole.vo.SysRoleVo;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author ljl
* @description 针对表【sys_role(角色表)】的数据库操作Service实现
* @date 2023-12-07 23:02:53
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleResourceServiceImpl roleResourceRelationService;


    @Override
    public Boolean create(SysRoleAddDto addReq) {
        //判断角色是否存在
        if (sysRoleMapper.selectCount(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleKey, addReq.getRoleKey())) > 0) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "角色已存在");
        }
        SysRole sysRole =  EntityConverter.copyAndGetSingle(addReq, SysRole.class);
        //添加创建人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        sysRole.setCreateName(loginUser.getUserAccount());
        return sysRoleMapper.insert(sysRole) > 0;
    }

    @Override
    public Boolean update(SysRoleEditDto editReq) {
        //校验角色是否重复
        if (sysRoleMapper.selectCount(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleKey, editReq.getRoleKey())
                .ne(SysRole::getId, editReq.getId())) > 0) {
            throw new BusinessException("角色已存在");
        }
        //校验角色是否存在
        if (BeanUtil.isEmpty(sysRoleMapper.selectById(editReq.getId()))) {
            throw new BusinessException("该角色不存在");
        }
        SysRole sysRole =  EntityConverter.copyAndGetSingle(editReq, SysRole.class);
        //添加更新人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        sysRole.setUpdateName(loginUser.getUserAccount());
        return sysRoleMapper.updateById(sysRole) > 0;
    }

    @Override
    public Boolean delete(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            SysRole sysRole = sysRoleMapper.selectById(id);
            if (BeanUtil.isEmpty(sysRole)) {
                throw new BusinessException("该角色不存在");
            }
            return sysRoleMapper.deleteById(id) > 0;
        }
        return true;
    }

    @Override
    public PageResult<SysRoleVo> queryByPage(SysRoleQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());

        //分页条件查询角色信息
        List<SysRole> roleList = sysRoleMapper.selectList(new LambdaQueryWrapper<SysRole>()
                .like(StrUtil.isNotBlank(queryReq.getRoleName()), SysRole::getRoleName, queryReq.getRoleName())
                .eq(StrUtil.isNotBlank(queryReq.getStatus()), SysRole::getStatus, queryReq.getStatus()));
        List<SysRoleVo> pageList = EntityConverter.copyAndGetList(roleList, SysRoleVo.class);
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @Override
    public SysRoleVo queryById(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            SysRole sysRole = sysRoleMapper.selectOne(new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getId, id));
            if (BeanUtil.isEmpty(sysRole)) {
                throw new BusinessException("该角色不存在");
            }
            return EntityConverter.copyAndGetSingle(sysRole, SysRoleVo.class);
        }
        return null;
    }

    @Override
    public Boolean updateResource(SysRoleResourceDto sysRoleResourceDto) {
        //先删除原有关系
        QueryWrapper<SysRoleResource> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleResource::getRoleId,sysRoleResourceDto.getRoleId());
        roleResourceRelationService.remove(wrapper);
        //批量插入新关系
        List<SysRoleResource> relationList = new ArrayList<>();
        for (Long resourceId : sysRoleResourceDto.getResourceIds()) {
            SysRoleResource relation = new SysRoleResource();
            relation.setRoleId(sysRoleResourceDto.getRoleId());
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        return roleResourceRelationService.saveBatch(relationList);
    }
}




