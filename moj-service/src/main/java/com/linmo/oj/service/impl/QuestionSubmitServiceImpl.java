package com.linmo.oj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.mapper.QuestionMapper;
import com.linmo.oj.mapper.QuestionSubmitMapper;
import com.linmo.oj.model.enums.LanguageEnum;
import com.linmo.oj.model.enums.QuestionSubmitStatusEnum;
import com.linmo.oj.model.question.Question;
import com.linmo.oj.model.questionsubmit.QuestionSubmit;
import com.linmo.oj.model.questionsubmit.dto.QuestionSubmitAddDto;
import com.linmo.oj.model.questionsubmit.dto.QuestionSubmitQueryDto;
import com.linmo.oj.model.questionsubmit.vo.QuestionSubmitVo;
import com.linmo.oj.model.questionsubmit.vo.SubmitSummaryVo;
import com.linmo.oj.model.user.User;
import com.linmo.oj.model.user.vo.UserVo;
import com.linmo.oj.service.QuestionSubmitService;
import com.linmo.oj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author ljl
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-12-13 20:39:23
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserService userService;

    @Override
    public QuestionSubmit doQuestionSubmit(QuestionSubmitAddDto addReq) {
        // 校验编程语言是否合法
        String language = addReq.getLanguage();
        LanguageEnum languageEnum = LanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "不支持的编程语言");
        }
        long questionId = addReq.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            throw new BusinessException("题目不存在");
        }

        //判断用户是否有正在等待或判题的题，如果有，提交判题失败
        Long userId = userService.getLoginUser().getId();
        QuestionSubmit submit = lambdaQuery().eq(QuestionSubmit::getUserId, userId)
                .and(wrapper -> wrapper.eq(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.WAITING).or()
                        .eq(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.RUNNING)).one();
        if(submit != null){
            throw new BusinessException("提交过于频繁！");
        }

        //将question的提交数+1
        questionMapper.update(null, new UpdateWrapper<Question>()
                .setSql("submit_num = submit_num + 1").eq("id", question.getId()));

        // 是否已提交题目
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(addReq.getCode());
        questionSubmit.setLanguage(language);
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save){
            throw new BusinessException("数据插入失败");
        }
        // 执行判题服务
        return null;
    }

    @Override
    public PageResult<QuestionSubmitVo> queryByPage(QuestionSubmitQueryDto queryReq) {
        Long userId = userService.getLoginUser().getId();
        Page<User> page = PageHelper.startPage(queryReq.getPageIndex(), queryReq.getPageSize());
        //分页条件查询公告信息
        List<QuestionSubmit> roleList = baseMapper.selectList(new LambdaQueryWrapper<QuestionSubmit>()
                .select(QuestionSubmit.class, item -> !item.getColumn().equals("code"))
                .eq(QuestionSubmit::getUserId, userId).eq(QuestionSubmit::getQuestionId, queryReq.getQuestionId()));
        List<QuestionSubmitVo> pageList = EntityConverter.copyAndGetList(roleList, QuestionSubmitVo.class);
        return new PageResult<>(pageList, page.getTotal(), page.getPageNum(), page.getPageSize());
    }

    @Override
    public SubmitSummaryVo getSubmitSummary() {
        SubmitSummaryVo summaryVo = new SubmitSummaryVo();

        UserVo currentUser = userService.getLoginUser();

        //获取简单、中等、困难题目ids
        List<Long> easyIds = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                        .select(Question::getId).eq(Question::getDifficulty, "0"))
                .stream().map(Question::getId).collect(Collectors.toList());
        List<Long> mediumIds = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                        .select(Question::getId).eq(Question::getDifficulty, "1"))
                .stream().map(Question::getId).collect(Collectors.toList());
        List<Long> hardIds = questionMapper.selectList(new LambdaQueryWrapper<Question>()
                        .select(Question::getId).eq(Question::getDifficulty, "2"))
                .stream().map(Question::getId).collect(Collectors.toList());
        int easyTotal = easyIds.size();
        int mediumTotal = mediumIds.size();
        int hardTotal = hardIds.size();
        summaryVo.setEasyTotal(easyTotal);
        summaryVo.setMediumTotal(mediumTotal);
        summaryVo.setHardTotal(hardTotal);
        summaryVo.setTotal(easyTotal + mediumTotal + hardTotal);

        //获取用户通过的简单、中等、困难题目数
        Integer easyPass = baseMapper.getPassCount(currentUser.getId(), easyIds);
        Integer mediumPass = baseMapper.getPassCount(currentUser.getId(), mediumIds);
        Integer hardPass = baseMapper.getPassCount(currentUser.getId(), hardIds);
        summaryVo.setEasyPass(easyPass);
        summaryVo.setMediumPass(mediumPass);
        summaryVo.setHardPass(hardPass);

        //获取用户提交总数
        Long submitCount = baseMapper.selectCount(new LambdaQueryWrapper<QuestionSubmit>()
                .eq(QuestionSubmit::getUserId, currentUser.getId()));
        summaryVo.setSubmitCount(submitCount);
        //获取用户成功的提交
        Long passCount = baseMapper.selectCount(new LambdaQueryWrapper<QuestionSubmit>()
                .eq(QuestionSubmit::getUserId, currentUser.getId())
                .eq(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.SUCCEED.getValue()));
        summaryVo.setPassCount(passCount);

        return summaryVo;
    }

    @Override
    public QuestionSubmitVo queryById(Long id) {
        if (BeanUtil.isNotEmpty(id)) {
            QuestionSubmit questionSubmit = baseMapper.selectOne(new LambdaQueryWrapper<QuestionSubmit>()
                    .eq(QuestionSubmit::getId, id));
            if (BeanUtil.isEmpty(questionSubmit)) {
                throw new BusinessException("没有提交记录");
            }
            return EntityConverter.copyAndGetSingle(questionSubmit, QuestionSubmitVo.class);
        }
        return null;
    }

}




