package com.linmo.oj.mapper;

import com.linmo.oj.model.sysresource.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ljl
* @description 针对表【sys_resource(后台资源表)】的数据库操作Mapper
* @createDate 2023-12-07 16:07:15
* @Entity com.linmo.springboot.model.sysresource.SysResource
*/
public interface SysResourceMapper extends BaseMapper<SysResource> {

    /**
     * 获取用户所有可访问资源
     */
    List<SysResource> getResourceList(@Param("userId") Long userId);

    /**
     * 根据角色ID获取资源
     */
    List<SysResource> getResourceListByRoleId(@Param("roleId") Long roleId);

}




