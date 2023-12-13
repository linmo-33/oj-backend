package com.linmo.oj.mapper;

import com.linmo.oj.model.sysrole.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ljl
* @description 针对表【sys_role(角色表)】的数据库操作Mapper
* @createDate 2023-12-07 23:02:53
* @Entity com.linmo.springboot.model.sysrole.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取用户所有角色
     */
    List<SysRole> getRoleList(@Param("userId") Long userId);

}




