package com.linmo.oj.common.exception;


import cn.dev33.satoken.exception.NotLoginException;
import com.linmo.oj.common.api.BaseResponse;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.api.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ResultCode.SYSTEM_ERROR, e.getMessage());
    }

    /**
     * 登录权限校验
     */
    @ExceptionHandler({NotLoginException.class})
    public BaseResponse<?> unauthorized(NotLoginException nle) {
        log.error("NotLoginException", nle);
        return ResultUtils.error(ResultCode.NO_AUTH_ERROR, nle.getMessage());
    }

    /**
     * 校验传参
     */
    @ExceptionHandler(ValidationException.class)
    public BaseResponse<?> handleBadRequest(Exception e) {
        return ResultUtils.error(ResultCode.PARAMS_ERROR, e.getMessage());
    }
}
