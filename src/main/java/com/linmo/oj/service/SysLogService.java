package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.syslog.SysLog;
import com.linmo.oj.model.syslog.dto.SysLogQueryDto;
import com.linmo.oj.model.syslog.vo.SysLogVo;

/**
* @author ljl
* @description 针对表【sys_log(系统日志)】的数据库操作Service
* @createDate 2023-12-13 14:54:34
*/
public interface SysLogService extends IService<SysLog> {

    /**
     * 分页查询资源
     */
    PageResult<SysLogVo> queryByPage(SysLogQueryDto queryReq);

}
