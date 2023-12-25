package com.linmo.oj.commons;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * 
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private  boolean success;

    public BaseResponse(int code, T data, String message,boolean success) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public BaseResponse(int code, T data,boolean success) {
        this(code, data, "",success);
    }

    public BaseResponse(ResultCode resultCode) {
        this(resultCode.getCode(), null, resultCode.getMessage(),false);
    }
}
