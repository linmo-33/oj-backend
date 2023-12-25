package com.linmo.oj.commons;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     *
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ResultCode.SUCCESS.getCode(), data, ResultCode.SUCCESS.getMessage(), true);
    }

    /**
     * 失败
     *
     */
    public static BaseResponse error(ResultCode resultCode) {
        return new BaseResponse<>(resultCode);
    }

    /**
     * 失败
     *
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message,false);
    }

    /**
     * 失败
     *
     */
    public static BaseResponse error(ResultCode resultCode, String message) {
        return new BaseResponse(resultCode.getCode(), null, message,false);
    }

    /**
     * 未授权返回结果
     *
     */
    public static <T> BaseResponse<T> forbidden(T data) {
        return new BaseResponse<>(ResultCode.FORBIDDEN_ERROR.getCode(), data, ResultCode.FORBIDDEN_ERROR.getMessage(), false);
    }

    /**
     * 未登录返回结果
     */
    public static <T> BaseResponse<T> unauthorized(T data) {
        return new BaseResponse<T>(ResultCode.NOT_LOGIN_ERROR.getCode(), data,ResultCode.NOT_LOGIN_ERROR.getMessage(), false);
    }
}
