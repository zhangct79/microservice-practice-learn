package com.zhangct.example.base;

/**
 * @author zhangct
 * @date 20/8/7
 */


public enum ResultEnum {
    UNKNOW_ERROR(-1,"未知错误"),
    SUCCESS(0,"成功"),
    USER_NOT_EXISTS(1,"用户不存在"),
    USER_IS_EXISTS(2,"用户已存在"),
    TOKEN_PAST(301, "token过期"),
    TOKEN_ERROR(302, "token异常"),
    LECTURER_REQUISITION_REGISTERED(501, "用户或密码错误"),
    INVALID_PARAMETERS(1001, "参数异常");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
