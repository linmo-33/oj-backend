package com.linmo.oj.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.mapper.QuestionMapper;
import com.linmo.oj.mapper.QuestionSubmitMapper;
import com.linmo.oj.model.enums.QuestionSubmitStatusEnum;
import com.linmo.oj.model.question.Question;
import com.linmo.oj.model.question.dto.*;
import com.linmo.oj.model.question.vo.QuestionVo;
import com.linmo.oj.model.questionsubmit.QuestionSubmit;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ljl
 * @description 针对表【question(题目)】的数据库操作Service实现
 * @createDate 2023-12-13 20:39:15
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionSubmitMapper questionSubmitMapper;

    private final static Gson GSON = new Gson();

    /**
     * 新增题目
     *
     * @param addReq 题目信息
     * @return 是否成功
     */
    @Override
    public Boolean create(QuestionAddDto addReq) {
        //判断题目是否存在
        if (questionMapper.selectCount(new LambdaQueryWrapper<Question>()
                .eq(Question::getTitle, addReq.getTitle())) > 0) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "题目已存在");
        }
        Question question = EntityConverter.copyAndGetSingle(addReq, Question.class);
        List<String> tags = addReq.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = addReq.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = addReq.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        //添加创建人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        question.setCreateName(loginUser.getUserAccount());
        return questionMapper.insert(question) > 0;
    }

    /**
     * 修改题目信息
     *
     * @param editReq 题目信息
     * @return 是否成功
     */
    @Override
    public Boolean update(QuestionEditDto editReq) {
        //校验题目是否重复
        if (questionMapper.selectCount(new LambdaQueryWrapper<Question>()
                .eq(Question::getTitle, editReq.getTitle())
                .ne(Question::getId, editReq.getId())) > 0) {
            throw new BusinessException("题目已存在");
        }
        //校验题目是否存在
        if (BeanUtil.isEmpty(questionMapper.selectById(editReq.getId()))) {
            throw new BusinessException("该题目不存在");
        }
        Question question = EntityConverter.copyAndGetSingle(editReq, Question.class);
        List<String> tags = editReq.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }
        List<JudgeCase> judgeCase = editReq.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }
        JudgeConfig judgeConfig = editReq.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }
        //添加更新人
        UserVo loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        question.setUpdateName(loginUser.getUserAccount());
        return questionMapper.updateById(question) > 0;
    }

    /**
     * 删除指定题目
     *
     * @param id 题目id
     * @return 是否成功
     */
    @Override
    public Boolean delete(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            Question question = questionMapper.selectById(id);
            if (BeanUtil.isEmpty(question)) {
                throw new BusinessException("该题目不存在");
            }
            return questionMapper.deleteById(id) > 0;
        }
        return true;
    }

    /**
     * 获取分页查询条件
     *
     * @param queryReq 查询条件
     * @return QueryWrapper<Question>
     */
    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryDto queryReq) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        String status = queryReq.getStatus();
        if (StrUtil.isNotBlank(status) && !status.equals("全部")) {
            UserVo currentUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
            Set<Long> passedIds;
            Set<Long> triedIds;
            switch (status) {
                case "已通过":
                    passedIds = questionSubmitMapper.selectList(new LambdaQueryWrapper<QuestionSubmit>()
                                    .select(QuestionSubmit::getQuestionId).eq(QuestionSubmit::getUserId, currentUser.getId())
                                    .eq(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.SUCCEED.getValue()))
                            .stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
                    if (passedIds.isEmpty()) {
                        return null;
                    }
                    queryWrapper.in("id", passedIds);
                    break;
                case "尝试过":
                    passedIds = questionSubmitMapper.selectList(new LambdaQueryWrapper<QuestionSubmit>()
                                    .select(QuestionSubmit::getQuestionId).eq(QuestionSubmit::getUserId, currentUser.getId())
                                    .eq(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.SUCCEED.getValue()))
                            .stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
                    triedIds = questionSubmitMapper.selectList(new LambdaQueryWrapper<QuestionSubmit>()
                                    .select(QuestionSubmit::getQuestionId).eq(QuestionSubmit::getUserId, currentUser.getId())
                                    .ne(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.SUCCEED.getValue()))
                            .stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
                    triedIds = (Set<Long>) CollUtil.subtract(triedIds, passedIds);
                    if (triedIds.isEmpty()) {
                        return null;
                    }
                    queryWrapper.in("id", triedIds);
                    break;
                case "未开始":
                    triedIds = questionSubmitMapper.selectList(new LambdaQueryWrapper<QuestionSubmit>()
                                    .select(QuestionSubmit::getQuestionId).eq(QuestionSubmit::getUserId, currentUser.getId()))
                            .stream().map(QuestionSubmit::getQuestionId).collect(Collectors.toSet());
                    if (!triedIds.isEmpty()) {
                        queryWrapper.notIn("id", triedIds);
                    }
                    break;
            }
        }
        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getTitle()), "title", queryReq.getTitle())
                .like(StringUtils.isNotBlank(queryReq.getContent()), "content", queryReq.getContent())
                .eq(StringUtils.isNotBlank(queryReq.getDifficulty()), "difficulty", queryReq.getDifficulty());
        if (CollectionUtils.isNotEmpty(queryReq.getTags())) {
            for (String tag : queryReq.getTags()) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        return queryWrapper;
    }
    
    /**
     * 转换题目信息
     *
     */
    public QuestionVo getQuestionVoPage(Question question){
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);
        questionVo.setTags(JSONUtil.toList(question.getTags(), String.class));
        questionVo.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class));
        UserVo  loginUser = EntityConverter.copyAndGetSingle(StpUtil.getSession().get("loginUser"), UserVo.class);
        //查询当前用户历史做题信息（已通过、尝试过、未开始）
        QuestionSubmit submit = questionSubmitMapper.selectOne(new QueryWrapper<QuestionSubmit>()
                .select("max(status) as status").lambda()
                .eq(QuestionSubmit::getQuestionId, question.getId())
                .eq(QuestionSubmit::getUserId, loginUser.getId()));

        if (submit == null) {
            questionVo.setStatus("未开始");
        } else if (submit.getStatus().equals(QuestionSubmitStatusEnum.SUCCEED.getValue())) {
            questionVo.setStatus("已通过");
        } else if (submit.getStatus().equals(QuestionSubmitStatusEnum.FAILED.getValue())) {
            questionVo.setStatus("尝试过");
        } else {
            questionVo.setStatus("未开始");
        }

        return questionVo;
    }

    /**
     * 分页查询资源
     *
     * @param queryReq 查询条件
     * @return PageResult<QuestionVo>
     */
    @Override
    public PageResult<QuestionVo> queryByPage(QuestionQueryDto queryReq) {
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());
        Wrapper<Question> queryWrapper = getQueryWrapper(queryReq);
        List<Question> questionList = questionMapper.selectList(queryWrapper);
        List<QuestionVo> pageList = questionList.stream().map(this::getQuestionVoPage).collect(Collectors.toList());
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }



    /**
     * 根据id查询题目信息（普通用户）
     *
     * @param id 题目id
     * @return QuestionVo
     */
    @Override
    public QuestionVo queryById(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            Question question = questionMapper.selectOne(new LambdaQueryWrapper<Question>()
                    .eq(Question::getId, id));
            if (BeanUtil.isEmpty(question)) {
                throw new BusinessException("该角色不存在");
            }
            return EntityConverter.copyAndGetSingle(question, QuestionVo.class);
        }
        return null;
    }

    /**
     * 获取题目标签
     *
     * @return 题目标签列表
     */
    @Override
    public List<String> getQuestionTags() {
        return lambdaQuery().select(Question::getTags).list().stream()
                .flatMap(question -> JSONUtil.toList(question.getTags(), String.class).stream())
                .distinct().collect(Collectors.toList());
    }
}




