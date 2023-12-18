package com.linmo.oj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linmo.oj.common.api.PageResult;
import com.linmo.oj.model.question.Question;
import com.linmo.oj.model.question.dto.QuestionAddDto;
import com.linmo.oj.model.question.dto.QuestionEditDto;
import com.linmo.oj.model.question.dto.QuestionQueryDto;
import com.linmo.oj.model.question.vo.QuestionVo;

import java.util.List;


/**
* @author ljl
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-12-13 20:39:15
*/
public interface QuestionService extends IService<Question> {

    /**
     * 新增题目
     */
    Boolean create(QuestionAddDto addReq);

    /**
     * 修改题目信息
     */
    Boolean update(QuestionEditDto editReq);

    /**
     * 删除指定题目
     */
    Boolean delete(Long id);

    /**
     * 获取分页查询条件
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryDto queryReq);

    /**
     * 分页查询资源（普通用户）
     */
    PageResult<QuestionVo> queryByPage(QuestionQueryDto queryReq);

    /**
     * 分页查询资源（管理员）
     */
    //PageResult<Question> queryByPageAdmin(QuestionQueryDto queryReq);

    /**
     * 根据id查询题目信息（普通用户）
     */
    QuestionVo queryById(Long id);

    /**
     * 获取题目标签
     */
    List<String> getQuestionTags();

}
