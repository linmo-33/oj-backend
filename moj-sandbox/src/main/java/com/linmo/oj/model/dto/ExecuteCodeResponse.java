package com.linmo.oj.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author linmo
 * @description TODO
 * @date 2023-10-16 16:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    /**
     * 执行信息
     */
    private String msg;
    /**
     * 执行状态
     */
    private Integer code;
    /**
     * 执行结果
     */
    private List<ExecuteResult> results;

}