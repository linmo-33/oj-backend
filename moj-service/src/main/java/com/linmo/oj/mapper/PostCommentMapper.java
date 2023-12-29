package com.linmo.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linmo.oj.model.postcomment.PostComment;
import com.linmo.oj.model.postcomment.vo.PostCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ljl
* @description 针对表【post_comment(帖子评论)】的数据库操作Mapper
* @createDate 2023-12-21 10:46:27
* @Entity com.linmo.oj.model.postcomment.PostComment
*/
public interface PostCommentMapper extends BaseMapper<PostComment> {

    List<PostCommentVo> queryByPostId(@Param("postId") Long postId);

}




