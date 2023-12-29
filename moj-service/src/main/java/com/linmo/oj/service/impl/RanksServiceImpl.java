package com.linmo.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmo.oj.model.ranks.Ranks;
import com.linmo.oj.service.RanksService;
import com.linmo.oj.mapper.RanksMapper;
import org.springframework.stereotype.Service;

/**
* @author ljl
* @description 针对表【ranks】的数据库操作Service实现
* @createDate 2023-12-26 16:25:19
*/
@Service
public class RanksServiceImpl extends ServiceImpl<RanksMapper, Ranks>
    implements RanksService{

}




