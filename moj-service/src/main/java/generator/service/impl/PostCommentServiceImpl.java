package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.model.PostComment;
import generator.service.PostCommentService;
import generator.mapper.PostCommentMapper;
import org.springframework.stereotype.Service;

/**
* @author ljl
* @description 针对表【post_comment(帖子评论)】的数据库操作Service实现
* @createDate 2023-12-21 10:46:27
*/
@Service
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment>
    implements PostCommentService{

}




