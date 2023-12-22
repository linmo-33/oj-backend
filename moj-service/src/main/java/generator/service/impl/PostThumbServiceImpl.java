package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.model.PostThumb;
import generator.service.PostThumbService;
import generator.mapper.PostThumbMapper;
import org.springframework.stereotype.Service;

/**
* @author ljl
* @description 针对表【post_thumb(帖子点赞)】的数据库操作Service实现
* @createDate 2023-12-21 10:48:10
*/
@Service
public class PostThumbServiceImpl extends ServiceImpl<PostThumbMapper, PostThumb>
    implements PostThumbService{

}




