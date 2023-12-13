package com.linmo.oj.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.linmo.oj.mapper.SysResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author ljl
 *
 */
@Service
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private SysResourceMapper resourceMapper;

    /**
     * 从Redis中获取权限码集合
     * 返回一个账号所拥有的权限码集合
     * @param loginId 账号id
     * @param loginType 账号类型
     * @return 该账号id所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return (List<String>) StpUtil.getSession().get("resourceCodeList");
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     * @param loginId 账号id
     * @param loginType 账号类型
     * @return 该账号id所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return (List<String>) StpUtil.getSession().get("roleList");
    }
}

