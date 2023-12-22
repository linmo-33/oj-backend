package com.linmo.oj.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.mapper.SysNoticeMapper;
import com.linmo.oj.model.sysnotice.SysNotice;
import com.linmo.oj.model.sysnotice.dto.SysNoticeAddDto;
import com.linmo.oj.model.sysnotice.dto.SysNoticeEditDto;
import com.linmo.oj.model.sysnotice.dto.SysNoticeQueryDto;
import com.linmo.oj.model.sysnotice.vo.SysNoticeVo;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.SysNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author ljl
* @description 针对表【sys_notice(系统公告)】的数据库操作Service实现
* @createDate 2023-12-13 14:54:20
*/
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice>
    implements SysNoticeService{

    @Resource
    private SysNoticeMapper sysNoticeMapper;


    @Override
    public Boolean create(SysNoticeAddDto addReq) {
        SysNotice sysNotice = EntityConverter.copyAndGetSingle(addReq, SysNotice.class);
        //添加创建人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        sysNotice.setCreateName(loginUser.getUserAccount());
        return sysNoticeMapper.insert(sysNotice) > 0;
    }

    @Override
    public Boolean update(SysNoticeEditDto editReq) {
        //校验公告是否存在
        if (BeanUtil.isEmpty(sysNoticeMapper.selectById(editReq.getId()))) {
            throw new BusinessException("该公告不存在");
        }
        SysNotice sysNotice = EntityConverter.copyAndGetSingle(editReq, SysNotice.class);
        //添加更新人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        sysNotice.setUpdateName(loginUser.getUserAccount());
        return sysNoticeMapper.updateById(sysNotice) > 0;
    }

    @Override
    public Boolean delete(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            SysNotice sysNotice = sysNoticeMapper.selectById(id);
            if (BeanUtil.isEmpty(sysNotice)) {
                throw new BusinessException("该公告不存在");
            }
            return sysNoticeMapper.deleteById(id) > 0;
        }
        return true;
    }

    @Override
    public PageResult<SysNoticeVo> queryByPage(SysNoticeQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());

        //分页条件查询公告信息
        List<SysNotice> roleList = sysNoticeMapper.selectList(new LambdaQueryWrapper<SysNotice>()
                .like(StrUtil.isNotBlank(queryReq.getTitle()), SysNotice::getTitle, queryReq.getTitle())
                .eq(StrUtil.isNotBlank(queryReq.getStatus()), SysNotice::getStatus, queryReq.getStatus()));
        List<SysNoticeVo> pageList = EntityConverter.copyAndGetList(roleList, SysNoticeVo.class);
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @Override
    public SysNoticeVo queryById(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            SysNotice sysNotice = sysNoticeMapper.selectOne(new LambdaQueryWrapper<SysNotice>()
                    .eq(SysNotice::getId, id));
            if (BeanUtil.isEmpty(sysNotice)) {
                throw new BusinessException("该公告不存在");
            }
            return EntityConverter.copyAndGetSingle(sysNotice, SysNoticeVo.class);
        }
        return null;
    }
}




