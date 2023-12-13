package com.linmo.oj.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.model.sysresource.SysResource;
import com.linmo.oj.model.sysresource.dto.SysResourceAddDto;
import com.linmo.oj.model.sysresource.dto.SysResourceEditDto;
import com.linmo.oj.model.sysresource.dto.SysResourceQueryDto;
import com.linmo.oj.model.sysresource.vo.SysResourceVo;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.SysResourceService;
import com.linmo.oj.mapper.SysResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ljl
* @description 针对表【sys_resource(后台资源表)】的数据库操作Service实现
* @createDate 2023-12-07 16:07:15
*/
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource>
    implements SysResourceService{
    
    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    public Boolean create(SysResourceAddDto addReq) {
        //判断资源是否存在
        if (sysResourceMapper.selectCount(new LambdaQueryWrapper<SysResource>()
                .eq(SysResource::getResourceCode, addReq.getResourceCode())) > 0) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "资源已存在");
        }
        //添加创建人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        addReq.setCreateName(loginUser.getUserAccount());
        return sysResourceMapper.insert(EntityConverter.copyAndGetSingle(addReq, SysResource.class)) > 0;
    }

    @Override
    public Boolean update(SysResourceEditDto editReq) {
        //校验资源是否重复
        if (sysResourceMapper.selectCount(new LambdaQueryWrapper<SysResource>()
                .eq(SysResource::getResourceCode, editReq.getResourceCode())
                .ne(SysResource::getId, editReq.getId())) > 0) {
            throw new BusinessException("资源已存在");
        }
        //校验资源是否存在
        if (BeanUtil.isEmpty(sysResourceMapper.selectById(editReq.getId()))) {
            throw new BusinessException("该资源不存在");
        }
        //添加更新人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        editReq.setUpdateName(loginUser.getUserAccount());
        return sysResourceMapper.updateById(EntityConverter.copyAndGetSingle(editReq, SysResource.class)) > 0;
    }

    @Override
    public Boolean delete(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            SysResource sysRole = sysResourceMapper.selectById(id);
            if (BeanUtil.isEmpty(sysRole)) {
                throw new BusinessException("该资源不存在");
            }
            return sysResourceMapper.deleteById(id) > 0;
        }
        return true;
    }

    @Override
    public PageResult<SysResourceVo> queryByPage(SysResourceQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());

        //分页条件查询资源信息
        List<SysResource> roleList = sysResourceMapper.selectList(new LambdaQueryWrapper<SysResource>()
                .like(StrUtil.isNotBlank(queryReq.getResourceName()), SysResource::getResourceName, queryReq.getResourceName())
                .like(StrUtil.isNotBlank(queryReq.getResourceCode()), SysResource::getResourceCode, queryReq.getResourceCode()));
        List<SysResourceVo> pageList = EntityConverter.copyAndGetList(roleList, SysResourceVo.class);
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }
    
}




