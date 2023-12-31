package com.linmo.oj.common.api;

/**
 * 自定义错误码
 */
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    PARAMS_ERROR(400, "请求参数错误"),
    NOT_LOGIN_ERROR(401, "未登录或token已经过期"),
    NO_AUTH_ERROR(401, "无权限"),
    NOT_FOUND_ERROR(404, "请求数据不存在"),
    FORBIDDEN_ERROR(403, "未授权"),
    SYSTEM_ERROR(500, "系统内部异常"),
    OPERATION_ERROR(501, "操作失败"),

    //服务器内部异常
    INTERNAL_SERVER_ERROR(505, "未知的服务器内部异常"),

    //OJ相关
    SUBMIT_ERROR(20001, "提交判题失败"),
    //代码沙箱
    DANGER_CODE(21001, "危险代码");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
