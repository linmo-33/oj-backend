package com.linmo.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.questionsubmit.QuestionSubmit;
import com.linmo.oj.model.questionsubmit.dto.QuestionSubmitAddDto;
import com.linmo.oj.model.questionsubmit.dto.QuestionSubmitQueryDto;
import com.linmo.oj.model.questionsubmit.vo.QuestionSubmitVo;
import com.linmo.oj.model.questionsubmit.vo.SubmitSummaryVo;

/**
* @author ljl
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-12-13 20:39:23
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 提交题目
     */
    QuestionSubmit doQuestionSubmit(QuestionSubmitAddDto addReq);

    /**
     * 分页查询题目提交历史
     */
    PageResult<QuestionSubmitVo> queryByPage(QuestionSubmitQueryDto queryReq);

    /**
     * 获取提交概况
     */
    SubmitSummaryVo getSubmitSummary();

    /**
     * 根据id查询提交记录
     */
    QuestionSubmitVo queryById(Long id);
}
