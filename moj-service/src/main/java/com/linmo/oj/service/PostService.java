package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.post.Post;
import com.linmo.oj.model.post.dto.PostAddDto;
import com.linmo.oj.model.post.dto.PostEditDto;
import com.linmo.oj.model.post.dto.PostQueryDto;
import com.linmo.oj.model.post.vo.PostVo;


/**
* @author ljl
* @description 针对表【post(帖子)】的数据库操作Service
* @createDate 2023-12-21 10:46:04
*/
public interface PostService extends IService<Post> {

    /**
     * 新增帖子
     */
    Boolean create(PostAddDto addReq);

    /**
     * 修改帖子信息
     */
    Boolean update(PostEditDto editReq);

    /**
     * 删除指定帖子
     */
    Boolean delete(Long id);

    /**
     * 分页查询帖子信息(管理员)
     */
    PageResult<PostVo> queryByPage(PostQueryDto queryReq);

    /**
     * 分页查询帖子信息(用户)
     */
    PageResult<PostVo> queryByPageUser(PostQueryDto queryReq);

    /**
     * 分页查询帖子信息(所有)
     */
    PageResult<PostVo> listAll(PostQueryDto queryReq);

    /**
     * 根据id查询帖子信息
     */
    PostVo queryById(Long id);


}
