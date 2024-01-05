package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.model.postcomment.PostComment;
import com.linmo.oj.model.postcomment.dto.PostCommentAddDto;
import com.linmo.oj.model.postcomment.vo.PostCommentVo;

import java.util.List;

/**
* @author ljl
* @description 针对表【post_comment(帖子评论)】的数据库操作Service
* @createDate 2023-12-21 10:46:27
*/
public interface PostCommentService extends IService<PostComment> {

    /**
     * 新增评论
     */
    Boolean create(PostCommentAddDto addReq);
    

    /**
     * 删除指定评论
     */
    Boolean delete(Long id);


    List<PostCommentVo> queryByPostId(Long postId);

}
