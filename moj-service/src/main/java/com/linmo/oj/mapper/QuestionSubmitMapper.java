package com.linmo.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linmo.oj.model.questionsubmit.QuestionSubmit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ljl
* @description 针对表【question_submit(题目提交)】的数据库操作Mapper
* @createDate 2023-12-13 20:39:23
* @Entity com.linmo.oj.model.questionsubmit.QuestionSubmit
*/
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

    Integer getPassCount(@Param("userId") Long userId, @Param("ids") List<Long> ids);

}




