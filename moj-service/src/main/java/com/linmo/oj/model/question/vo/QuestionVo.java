package com.linmo.oj.model.question.vo;

import cn.hutool.json.JSONUtil;
import com.linmo.oj.common.utils.EntityConverter;
import com.linmo.oj.model.question.Question;
import com.linmo.oj.model.question.dto.JudgeCase;
import com.linmo.oj.model.question.dto.JudgeConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户视图对象
 *
 * @author ljl
 * @since 2023-12-13 20:55
 */
@Data
public class QuestionVo {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "题目标题")
    private String title;

    @ApiModelProperty(value = "题目内容")
    private String content;

    @ApiModelProperty(value = "题目难度(0简单，1中等，2困难)")
    private String difficulty;

    @ApiModelProperty(value = "题目标签列表（json 数组）")
    private List<String> tags;

    @ApiModelProperty(value = "题目状态（已通过、尝试过、未开始）")
    private String status;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "题目提交数")
    private Integer submitNum;

    @ApiModelProperty(value = "题目通过数")
    private Integer acceptedNum;

    @ApiModelProperty(value = "判题用例（json数组）")
    private List<JudgeCase> judgeCase;

    @ApiModelProperty(value = "判题配置（json对象）")
    private JudgeConfig judgeConfig;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    /**
     * 包装类转对象
     *
     * @param questionVO 包装类
     * @return 对象
     */
    public static Question voToObj(QuestionVo questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = EntityConverter.copyAndGetSingle(questionVO, Question.class);
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if (voJudgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question 对象
     * @return 包装类
     */
    public static QuestionVo objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVo questionVO = EntityConverter.copyAndGetSingle(question, QuestionVo.class);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tagList);
        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return questionVO;
    }

}
