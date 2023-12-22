package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.sysresource.SysResource;
import com.linmo.oj.model.sysresource.dto.SysResourceAddDto;
import com.linmo.oj.model.sysresource.dto.SysResourceEditDto;
import com.linmo.oj.model.sysresource.dto.SysResourceQueryDto;
import com.linmo.oj.model.sysresource.vo.SysResourceVo;

/**
* @author ljl
* @description 针对表【sys_resource(后台资源表)】的数据库操作Service
* @createDate 2023-12-07 16:07:15
*/
public interface SysResourceService extends IService<SysResource> {

    /**
     * 添加资源
     */
    Boolean create(SysResourceAddDto addReq);

    /**
     * 修改资源
     */
    Boolean update(SysResourceEditDto editReq);

    /**
     * 删除资源
     */
    Boolean delete(Long id);

    /**
     * 分页查询资源
     */
    PageResult<SysResourceVo> queryByPage(SysResourceQueryDto queryReq);

}
