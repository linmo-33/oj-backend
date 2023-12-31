package com.linmo.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linmo.oj.model.postcomment.PostComment;
import com.linmo.oj.model.postcomment.dto.PostCommentAddDto;
import com.linmo.oj.model.postcomment.vo.PostCommentVo;
import com.linmo.oj.service.PostCommentService;
import com.linmo.oj.mapper.PostCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author ljl
* @description 针对表【post_comment(帖子评论)】的数据库操作Service实现
* @createDate 2023-12-21 10:46:27
*/
@Service
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment>
    implements PostCommentService{

    @Resource
    private PostCommentMapper postCommentMapper;

    @Override
    public Boolean create(PostCommentAddDto addReq) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public List<PostCommentVo> queryByPostId(Long postId) {

        return null;
    }
}




