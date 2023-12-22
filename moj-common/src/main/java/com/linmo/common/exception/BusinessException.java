//package com.linmo.common.exception;
//
//
//import com.linmo.oj.common.api.ResultCode;
//
///**
// * 自定义异常类
// */
//public class BusinessException extends RuntimeException {
//
//    /**
//     * 错误码
//     */
//    private final int code;
//
//    public BusinessException(String message) {
//        super(message);
//        this.code = 600;
//    }
//
//    public BusinessException(int code,String message) {
//        super(message);
//        this.code = code;
//    }
//
//    public BusinessException(ResultCode resultCode) {
//        super(resultCode.getMessage());
//        this.code = resultCode.getCode();
//    }
//
//    public BusinessException(ResultCode resultCode, String message) {
//        super(message);
//        this.code = resultCode.getCode();
//    }
//
//    public int getCode() {
//        return code;
//    }
//}
