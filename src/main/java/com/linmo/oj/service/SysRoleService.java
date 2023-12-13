package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.sysrole.SysRole;
import com.linmo.oj.model.sysrole.dto.SysRoleAddDto;
import com.linmo.oj.model.sysrole.dto.SysRoleEditDto;
import com.linmo.oj.model.sysrole.dto.SysRoleQueryDto;
import com.linmo.oj.model.sysrole.vo.SysRoleVo;

/**
* @author ljl
* @description 针对表【sys_role(角色表)】的数据库操作Service
* @createDate 2023-12-07 23:02:53
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 新增角色
     */
    Boolean create(SysRoleAddDto addReq);

    /**
     * 修改角色信息
     */
    Boolean update(SysRoleEditDto editReq);

    /**
     * 删除指定角色
     */
    Boolean delete(Long id);

    /**
     * 分页查询资源
     */
    PageResult<SysRoleVo> queryByPage(SysRoleQueryDto queryReq);

    /**
     * 根据id查询角色信息
     */
    SysRoleVo queryById(Long id);


}
