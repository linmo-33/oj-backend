package com.linmo.oj.controller;

import com.linmo.oj.common.aop.LogRecord;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultUtils;
import com.linmo.oj.model.post.dto.PostAddDto;
import com.linmo.oj.model.post.dto.PostEditDto;
import com.linmo.oj.model.post.dto.PostQueryDto;
import com.linmo.oj.model.post.vo.PostVo;
import com.linmo.oj.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "帖子接口")
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;


    @ApiOperation(value = "新增帖子")
    @PostMapping(value = "/create")
    @LogRecord(value = "新增帖子")
    public BaseResponse<Boolean> createPost(@Validated @RequestBody PostAddDto postAddDto) {
        return ResultUtils.success(postService.create(postAddDto));
    }

    @ApiOperation(value = "修改帖子")
    @PostMapping(value = "/update")
    @LogRecord(value = "修改帖子")
    public BaseResponse<Boolean> updatePost(@Validated @RequestBody PostEditDto postEditDto) {
        return ResultUtils.success(postService.update(postEditDto));
    }

    @ApiOperation(value = "删除帖子")
    @GetMapping(value = "/delete")
    @LogRecord(value = "删除帖子")
    public BaseResponse<Boolean> deletePost(@RequestParam("id") Long id) {
        return ResultUtils.success(postService.delete(id));
    }

    @ApiOperation(value = "根据id获取帖子")
    @GetMapping(value = "/queryById")
    public BaseResponse<PostVo> queryPostById(@RequestParam("id") Long id) {
        return ResultUtils.success(postService.queryById(id));
    }

    @ApiOperation(value = "分页查询帖子(管理员)")
    @PostMapping(value = "/queryList")
    public BaseResponse<PageResult<PostVo>> queryListPost(@Validated @RequestBody PostQueryDto postQueryDto) {
        return ResultUtils.success(postService.queryByPage(postQueryDto));
    }

    @ApiOperation(value = "分页查询帖子(当前登录用户)")
    @PostMapping(value = "/query")
    public BaseResponse<PageResult<PostVo>> queryListPostByUSer(@Validated @RequestBody PostQueryDto postQueryDto) {
        return ResultUtils.success(postService.queryByPageUser(postQueryDto));
    }

    @ApiOperation(value = "分页查询所有帖子")
    @PostMapping(value = "/listAll")
    public BaseResponse<PageResult<PostVo>> queryListPostAll(@Validated @RequestBody PostQueryDto postQueryDto) {
        return ResultUtils.success(postService.listAll(postQueryDto));
    }
}
