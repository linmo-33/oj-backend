package com.linmo.oj.model.questionsubmit.vo;

import cn.hutool.json.JSONUtil;
import com.linmo.oj.model.questionsubmit.QuestionSubmit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 题目提交封装类
 * @TableName question
 */
@Data
public class QuestionSubmitVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "编程语言")
    private String language;

    @ApiModelProperty(value = "用户代码")
    private String code;

    @ApiModelProperty(value = "判题信息")
    private JudgeInfo judgeInfo;

    @ApiModelProperty(value = "判题状态0-待判题、1-判题中、2-成功、3-失败")
    private Integer status;

    @ApiModelProperty(value = "题目 id")
    private Long questionId;

    @ApiModelProperty(value = "提交用户 id")
    private Long userId;

    @ApiModelProperty(value = "提交时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 对象转包装类
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVo objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVo questionSubmitVO = new QuestionSubmitVo();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class));
        return questionSubmitVO;
    }
    
}