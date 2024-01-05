package com.linmo.oj.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.mapper.PostMapper;
import com.linmo.oj.model.post.Post;
import com.linmo.oj.model.post.dto.PostAddDto;
import com.linmo.oj.model.post.dto.PostEditDto;
import com.linmo.oj.model.post.dto.PostQueryDto;
import com.linmo.oj.model.post.vo.PostVo;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.PostService;
import com.linmo.oj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author ljl
* @description 针对表【post(帖子)】的数据库操作Service实现
* @date 2023-12-21 10:46:04
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService{

    @Resource
    private PostMapper postMapper;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    @Override
    public Boolean create(PostAddDto addReq) {
        Post post = EntityConverter.copyAndGetSingle(addReq, Post.class);
        List<String> tags = addReq.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        //添加用户id
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        post.setUserId(loginUser.getId());
        return postMapper.insert(post) > 0;
    }

    @Override
    public Boolean update(PostEditDto editReq) {
        //校验帖子是否存在
        if (BeanUtil.isEmpty(postMapper.selectById(editReq.getId()))) {
            throw new BusinessException("该帖子不存在");
        }
        Post post = EntityConverter.copyAndGetSingle(editReq, Post.class);
        List<String> tags = editReq.getTags();
        if (tags != null) {
            post.setTags(GSON.toJson(tags));
        }
        //添加更新人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        post.setUpdateName(loginUser.getUserAccount());
        return postMapper.updateById(post) > 0;
    }

    @Override
    public Boolean delete(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            Post post = postMapper.selectById(id);
            if (BeanUtil.isEmpty(post)) {
                throw new BusinessException("该帖子不存在");
            }
            return postMapper.deleteById(id) > 0;
        }
        return true;
    }

    /**
     * 分页查询帖子信息(管理员)
     */
    @Override
    public PageResult<PostVo> queryByPage(PostQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());

        //分页条件查询帖子信息
        List<Post> postList = postMapper.selectList(new LambdaQueryWrapper<Post>()
                .like(StrUtil.isNotBlank(queryReq.getTitle()), Post::getTitle, queryReq.getTitle())
                .like(StrUtil.isNotBlank(queryReq.getContent()), Post::getContent, queryReq.getContent())
                .eq(StrUtil.isNotBlank(queryReq.getStatus()), Post::getStatus, queryReq.getStatus()));
        List<PostVo> pageList = postList.stream().map(PostVo::objToVo).collect(Collectors.toList());
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    /**
     * 分页查询帖子信息(用户)
     */
    @Override
    public PageResult<PostVo> queryByPageUser(PostQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        //分页条件查询帖子信息
        List<Post> postList = postMapper.selectList(new LambdaQueryWrapper<Post>()
                .like(StrUtil.isNotBlank(queryReq.getTitle()), Post::getTitle, queryReq.getTitle())
                .like(StrUtil.isNotBlank(queryReq.getContent()), Post::getContent, queryReq.getContent())
                .eq(Post::getUserId, loginUser.getId()));
        List<PostVo> pageList = postList.stream().map(PostVo::objToVo).collect(Collectors.toList());
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    /**
     * 分页查询帖子信息(所有)
     */
    @Override
    public PageResult<PostVo> listAll(PostQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());

        //分页条件查询帖子信息
        List<Post> postList = postMapper.selectList(new LambdaQueryWrapper<Post>()
                .like(StrUtil.isNotBlank(queryReq.getTitle()), Post::getTitle, queryReq.getTitle())
                .like(StrUtil.isNotBlank(queryReq.getContent()), Post::getContent, queryReq.getContent())
                .eq(Post::getStatus, '0'));

        // 关联查询用户信息
        Set<Long> userIdSet = postList.stream().map(Post::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        List<PostVo> pageList = postList.stream().map(post -> {
            PostVo postVo = PostVo.objToVo(post);
            Long userId = post.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            assert user != null;
            postVo.setUserName(user.getUserName());
            postVo.setUserAvatar(user.getUserAvatar());
            return postVo;
        }).collect(Collectors.toList());
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @Override
    public PostVo queryById(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            Post post = postMapper.selectOne(new LambdaQueryWrapper<Post>()
                    .eq(Post::getId, id));
            if (BeanUtil.isEmpty(post)) {
                throw new BusinessException("该帖子不存在");
            }
            User user = userService.getById(post.getUserId());
            PostVo postVo = PostVo.objToVo(post);
            postVo.setUserAvatar(user.getUserAvatar());
            postVo.setUserName(user.getUserName());
            return postVo;
        }
        return null;
    }

}




