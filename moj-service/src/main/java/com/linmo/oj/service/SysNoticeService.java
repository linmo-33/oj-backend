package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.sysnotice.SysNotice;
import com.linmo.oj.model.sysnotice.dto.SysNoticeAddDto;
import com.linmo.oj.model.sysnotice.dto.SysNoticeEditDto;
import com.linmo.oj.model.sysnotice.dto.SysNoticeQueryDto;
import com.linmo.oj.model.sysnotice.vo.SysNoticeVo;


/**
* @author ljl
* @description 针对表【sys_notice(系统公告)】的数据库操作Service
* @createDate 2023-12-13 14:54:20
*/
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 新增公告
     */
    Boolean create(SysNoticeAddDto addReq);

    /**
     * 修改公告信息
     */
    Boolean update(SysNoticeEditDto editReq);

    /**
     * 删除指定公告
     */
    Boolean delete(Long id);

    /**
     * 分页查询资源
     */
    PageResult<SysNoticeVo> queryByPage(SysNoticeQueryDto queryReq);

    /**
     * 根据id查询公告信息
     */
    SysNoticeVo queryById(Long id);

}
