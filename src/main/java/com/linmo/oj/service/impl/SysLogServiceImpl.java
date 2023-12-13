package com.linmo.oj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.mapper.SysLogMapper;
import com.linmo.oj.model.syslog.SysLog;
import com.linmo.oj.model.syslog.dto.SysLogQueryDto;
import com.linmo.oj.model.syslog.vo.SysLogVo;
import com.linmo.oj.model.user.User;
import com.linmo.oj.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ljl
* @description 针对表【sys_log(系统日志)】的数据库操作Service实现
* @createDate 2023-12-13 14:54:34
*/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
    implements SysLogService{

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public PageResult<SysLogVo> queryByPage(SysLogQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());

        //分页条件查询角色信息
        List<SysLog> roleList = sysLogMapper.selectList(new LambdaQueryWrapper<SysLog>()
                .like(StrUtil.isNotBlank(queryReq.getOperation()), SysLog::getOperation, queryReq.getOperation())
                .eq(StrUtil.isNotBlank(queryReq.getOperator()), SysLog::getOperator, queryReq.getOperator()));
        List<SysLogVo> pageList = EntityConverter.copyAndGetList(roleList, SysLogVo.class);
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }
}




