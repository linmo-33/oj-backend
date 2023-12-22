package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.model.Post;
import generator.service.PostService;
import generator.mapper.PostMapper;
import org.springframework.stereotype.Service;

/**
* @author ljl
* @description 针对表【post(帖子)】的数据库操作Service实现
* @createDate 2023-12-21 10:46:04
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

}




